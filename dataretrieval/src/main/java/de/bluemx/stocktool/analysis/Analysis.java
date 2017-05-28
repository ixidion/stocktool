package de.bluemx.stocktool.analysis;

import de.bluemx.stocktool.db.model.*;
import de.bluemx.stocktool.model.AnalystsOpinion;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import static de.bluemx.stocktool.analysis.LowLevelRules.*;

/**
 * Created by teclis on 23.01.17.
 */
public class Analysis {

    private List<AnalysisObject> anlysisList = new Vector<>();


    public AnalysisObject analyseRoE(StockquoteBasic basic) {
        ReturnOnEquity roe = (ReturnOnEquity) extractYear(basic.getLastestFetch().getReturnOnEquityList(), -1);
        AnalysisObject ao = new AnalysisObject();
        try {
            int result = applyRoeRule(roe.getTableValue());
            ao.setResult(result);
        } catch (RuntimeException e) {
            ao.setEx(e);
        } finally {
            ao.setFieldname("RULE01_ROE");
            anlysisList.add(ao);
            return ao;
        }
    }

    public AnalysisObject analyseEbitLY(StockquoteBasic basic) {
        EbitMargin ebitMargin = (EbitMargin) extractYear(basic.getLastestFetch().getEbitMarginList(), -1);
        boolean financialSector = basic.isFinancialSector();
        AnalysisObject ao = new AnalysisObject();
        try {
            int result = applyEbitMarginRule(ebitMargin.getTableValue(), financialSector);
            ao.setResult(result);
        } catch (RuntimeException e) {
            ao.setEx(e);
        } finally {
            ao.setFieldname("RULE02_EBIT_MARGIN");
            anlysisList.add(ao);
            return ao;
        }
    }

    public AnalysisObject analyseEquityRatioLY(StockquoteBasic basic) {
        EquityRatio equityRatio = (EquityRatio) extractYear(basic.getLastestFetch().getEquityRatioList(), -1);
        boolean financialSector = basic.isFinancialSector();
        AnalysisObject ao = new AnalysisObject();
        try {
            int result = applyEquityRatioRule(equityRatio.getTableValue(), financialSector);
            ao.setResult(result);
        } catch (RuntimeException e) {
            ao.setEx(e);
        } finally {
            ao.setFieldname("RULE03_EQUITY_RATIO");
            anlysisList.add(ao);
            return ao;
        }
    }

    public AnalysisObject analyseKGV5Years(StockquoteBasic basic) {
        BigDecimal yearMinus3 = extractYear(basic.getLastestFetch().getEpsList(), -1).getTableValue();
        BigDecimal yearMinus2 = extractYear(basic.getLastestFetch().getEpsList(), -1).getTableValue();
        BigDecimal yearMinus1 = extractYear(basic.getLastestFetch().getEpsList(), -1).getTableValue();
        BigDecimal year = extractYear(basic.getLastestFetch().getEpsList(), -1).getTableValue();
        BigDecimal yearPlus1 = extractYear(basic.getLastestFetch().getEpsList(), -1).getTableValue();
        BigDecimal quote = basic.getLastestFetch().getLatestQuote();
        AnalysisObject ao = new AnalysisObject();
        try {
            int result = applyAverageKGV5(yearMinus3, yearMinus2, yearMinus1, year, yearPlus1, quote);
            ao.setResult(result);
        } catch (RuntimeException e) {
            ao.setEx(e);
        } finally {
            ao.setFieldname("RULE04_AVERAGE_QUOTE_EARNINGS_RATIO");
            anlysisList.add(ao);
            return ao;
        }
    }

    public AnalysisObject analyseKGVActual(StockquoteBasic basic) {
        BigDecimal year = extractYear(basic.getLastestFetch().getEpsList(), -1).getTableValue();
        BigDecimal quote = basic.getLastestFetch().getLatestQuote();
        AnalysisObject ao = new AnalysisObject();
        try {
            int result = applyActualPriceEarningsRatio(year, quote);
            ao.setResult(result);
        } catch (RuntimeException e) {
            ao.setEx(e);
        } finally {
            ao.setFieldname("RULE05_ACTUAL_QUOTE_EARNINGS_RATIO");
            anlysisList.add(ao);
            return ao;
        }
    }

    public AnalysisObject analyseAnlystsOpinion(StockquoteBasic basic) {
        int analystsCount = basic.getLastestFetch().getAnalystsCount();
        AnalystsOpinion opinion = AnalystsOpinion.getOpinion(basic.getLastestFetch().getAnalystsOpinion());
        BigDecimal marketCap = basic.getLastestFetch().getMarketCapitalization();
        AnalysisObject ao = new AnalysisObject();
        try {
            int result = applyAnalystsOpinion(analystsCount, opinion, marketCap);
            ao.setResult(result);
        } catch (RuntimeException e) {
            ao.setEx(e);
        } finally {
            ao.setFieldname("RULE06_ANALYSTSOPINION");
            anlysisList.add(ao);
            return ao;
        }
    }

    public AnalysisObject analyseQuarterQuoteReaction(StockquoteBasic basic) {
        LocalDate quarterFigures = basic.getQuarterlyFigures();
        AnalysisObject ao = new AnalysisObject();
        try {
            LocalDate quarterFiguresOneDayBefore = basic.getQuarterlyFigures().minusDays(1);
            BigDecimal quaterQuote = basic.getLastestFetch().getQuoteFromDate(quarterFigures);
            BigDecimal quaterQuoteOneDayBefore = basic.getLastestFetch().getQuoteFromDateOrBefore(quarterFiguresOneDayBefore);
            int result = reactionToQuarterQuotes(quaterQuoteOneDayBefore, quaterQuote);
            ao.setResult(result);
        } catch (RuntimeException e) {
            ao.setEx(e);
        } finally {
            ao.setFieldname("RULE07_QUARTERQUOTE_REACTION");
            anlysisList.add(ao);
            return ao;
        }
    }

    public AnalysisObject analyseEarningsRevisions(StockquoteBasic basic) {
        // Intervall festlegen, in dem vorherige Eps gesucht werden.
        int intervalStart = 70;
        int intervalEnd = 20;
        int actualYear = LocalDate.now().getYear();
        AnalysisObject ao = new AnalysisObject();
        try {
            BigDecimal epsActualAY = basic.getLastestFetch().getEps(actualYear).getTableValue();
            int nextYear = actualYear + 1;
            BigDecimal epsActualNY = basic.getLastestFetch().getEps(nextYear).getTableValue();
            StockquoteDetail earlierFetch = basic.getFetches().stream()
                    .sorted(Comparator.comparing(StockquoteDetail::getFetchDate).reversed())
                    .filter(s -> s.getFetchDate().isBefore(LocalDate.now().minusDays(intervalEnd))
                            && s.getFetchDate().isAfter(LocalDate.now().minusDays(intervalEnd)))
                    .findFirst()
                    .get();
            if (earlierFetch != null) throw new RuntimeException("No earlier Fetch available.");
            BigDecimal epsEarlierFetchAY = earlierFetch.getEps(actualYear).getTableValue();
            BigDecimal epsEarlierFetchNY = earlierFetch.getEps(nextYear).getTableValue();

            int result = earningsRevision(epsEarlierFetchAY, epsActualAY, epsEarlierFetchNY, epsActualNY);
            ao.setResult(result);
        } catch (RuntimeException e) {
            ao.setEx(e);
        } finally {
            ao.setFieldname("RULE08_EARNINGSREVISION");
            anlysisList.add(ao);
            return ao;
        }
    }

    /**
     * @param yearList
     * @param year     0 is the actual business year, -1 is the last finished business without estimations
     */
    private <T extends TableKeyValues> TableKeyValues extractYear(List<T> yearList, int year) {
        Collections.sort(yearList, new YearComparator());
        Collections.reverse(yearList);
        LocalDate lastBusinessYear = null;
        for (TableKeyValues tKeyValue : yearList) {
            if (!tKeyValue.isEstimated()) {
                lastBusinessYear = tKeyValue.getTableYear();
                break;
            }
        }

        LocalDate yearToFind = lastBusinessYear.plusYears(year + 1);

        for (TableKeyValues tKeyValue : yearList) {
            if (tKeyValue.getTableYear().equals(yearToFind)) {
                return tKeyValue;
            }
        }
        throw new RuntimeException("Something went wrong here. Check data in DB.");
    }

}

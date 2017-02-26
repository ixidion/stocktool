package de.bluemx.stocktool.analysis;

import de.bluemx.stocktool.db.model.ReturnOnEquity;
import de.bluemx.stocktool.db.model.StockquoteBasic;
import de.bluemx.stocktool.db.model.TableKeyValues;
import de.bluemx.stocktool.db.model.YearComparator;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
/**
 * Created by teclis on 23.01.17.
 */
public class Analysis {

    private List<AnalysisObject> anlysisList = new Vector<>();


    public AnalysisObject analyseRoE(StockquoteBasic basic) {
        ReturnOnEquity roe = (ReturnOnEquity) extractYear(basic.getLastestFetch().getReturnOnEquityList(), -1);
        LowLevelRules rules = new LowLevelRules();
        AnalysisObject ao = new AnalysisObject();
        try {
            int result = rules.applyRoeRule(roe.getTableValue());
            ao.setResult(result);
        } catch (RuntimeException e) {
            ao.setEx(e);
        } finally {
            ao.setFieldname("RULE01_ROE");
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

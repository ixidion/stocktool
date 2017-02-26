package de.bluemx.stocktool.analysis;

import de.bluemx.stocktool.model.AnalystsOpinion;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import java.util.Vector;

import static de.bluemx.stocktool.helper.ArgsChecker.*;

/**
 * Created by teclis on 23.01.17.
 */
public final class LowLevelRules {

    private static final BigDecimal ROE_HIGH = new BigDecimal("20.0");
    private static final BigDecimal ROE_LOW = new BigDecimal("10.0");
    private static final BigDecimal EBITMARGIN_HIGH = new BigDecimal("12.0");
    private static final BigDecimal EBITMARGIN_LOW = new BigDecimal("6.0");
    private static final BigDecimal EQUITYRATIO_HIGH = new BigDecimal("25.0");
    private static final BigDecimal EQUITYRATIO_LOW = new BigDecimal("15.0");
    private static final BigDecimal EPS_GROWTH_HIGH = new BigDecimal("0.05");
    private static final BigDecimal EPS_GROWTH_LOW = new BigDecimal("-0.05");
    private static final BigDecimal fiveMrd = new BigDecimal("5000");
    private static final MathContext mathContext = new MathContext(32, RoundingMode.HALF_UP);


    /**
     * Rule #01
     * RoE value of last business year.
     * Normally these figures are available approx. 3 months after business year has ended.
     *
     * @param roeValue
     * @return
     * @throws NullPointerException if roeValue is null
     */
    public static int applyRoeRule(BigDecimal roeValue) {
        checkForNull(roeValue);
        if (roeValue.compareTo(ROE_HIGH) == 1) return 1;
        if (roeValue.compareTo(ROE_LOW) == -1) return -1;
        return 0;
    }

    /**
     * Rule #02
     * EbitMargin value of last business year.
     * Normally these figures are available approx. 3 months after business year has ended.
     *
     * @param ebitMargin
     * @return
     * @throws NullPointerException if ebitMargin is null
     */
    public static int applyEbitMarginRule(BigDecimal ebitMargin, boolean financial) {
        checkForNull(ebitMargin);
        if (financial) return 0;
        if (ebitMargin.compareTo(EBITMARGIN_HIGH) == 1) return 1;
        if (ebitMargin.compareTo(EBITMARGIN_LOW) == -1) return -1;
        return 0;
    }

    /**
     * Rule #03
     * Equity Ratio of the last business year.
     * For finance caps this has to be ignored
     *
     * @param equityRatio
     * @return
     * @throws NullPointerException if equityRatio is null
     */
    public static int applyEquityRatioRule(BigDecimal equityRatio) {
        checkForNull(equityRatio);
        if (equityRatio.compareTo(EQUITYRATIO_HIGH) == 1) return 1;
        if (equityRatio.compareTo(EQUITYRATIO_LOW) == -1) return -1;
        return 0;
    }

    /**
     * Rule #04
     * Computes the average PriceEarningsRatio of five years.
     *
     * @param yearNMinus3 3 years before actual year EPS
     * @param yearNMinus2 year before last year EPS
     * @param yearNMinus1 last year EPS
     * @param year        actual year EPS
     * @param yearNPlus1  estimated EPS next year
     * @param actualQuote todays quote
     * @return
     * @throws NullPointerException if any parameter is null
     */
    public static int applyAverageKGV5(BigDecimal yearNMinus3, BigDecimal yearNMinus2, BigDecimal yearNMinus1, BigDecimal year, BigDecimal yearNPlus1, BigDecimal actualQuote) {
        checkForNull(yearNMinus3);
        checkForNull(yearNMinus2);
        checkForNull(yearNMinus1);
        checkForNull(year);
        checkForNull(yearNPlus1);
        checkForNull(actualQuote);
        BigDecimal five = new BigDecimal("5.0");
        BigDecimal average = yearNMinus3.add(yearNMinus2).add(yearNMinus1).add(year).add(yearNPlus1).divide(five, mathContext);
        BigDecimal result = actualQuote.divide(average, mathContext);
        return evaluateKGV(result);
    }

    private static int evaluateKGV(BigDecimal result) {
        checkForNull(result);
        if (result.compareTo(new BigDecimal("12")) == -1) {
            return 1;
        } else if (result.compareTo(new BigDecimal("16")) == 1) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * Rule #05
     *
     * @param earningsActualYear
     * @param actualQUote
     * @return
     * @throws NullPointerException if any parameter is null
     */
    public static int applyActualPriceEarningsRatio(BigDecimal earningsActualYear, BigDecimal actualQUote) {
        checkForNull(earningsActualYear);
        checkForNull(actualQUote);
        BigDecimal result = actualQUote.divide(earningsActualYear, mathContext);
        return evaluateKGV(result);
    }

    /**
     * Rule #06
     *
     * @param analystsCount
     * @param analystsOpinion
     * @param marketCap
     * @return
     * @throws NullPointerException if analystsOpinion or marketCap is null
     * @throws IllegalArgumentException if analystsCount is 0
     */
    public static int applyAnalystsOpinion(int analystsCount, AnalystsOpinion analystsOpinion, BigDecimal marketCap) {
        int returnValue = 0;
        checkNumberGreaterThan(0, analystsCount);
        checkForNull(analystsOpinion);
        checkForNull(marketCap);

            if (analystsOpinion.equals(AnalystsOpinion.BUY) || analystsOpinion.equals(AnalystsOpinion.INCREASE)) {
                returnValue = 1;
            } else if (analystsOpinion.equals(AnalystsOpinion.SELL) || analystsOpinion.equals(AnalystsOpinion.DECREASE)) {
                returnValue = -1;
            } else {
                returnValue = 0;
            }
            // For Large Caps Analysts Opinion must be reversed or SM-Caps if analystsCount is more than 5
            if (marketCap.compareTo(fiveMrd) == 1 || analystsCount > 5) {
                returnValue *= -1;
            }
            return returnValue;

    }

    /**
     * Rule #07
     *
     * @param dayBefore
     * @param quarterQuotes
     * @return
     * @throws NullPointerException if any parameter is null
     */
    public static int reactionToQuarterQuotes(BigDecimal dayBefore, BigDecimal quarterQuotes) {
        checkForNull(dayBefore);
        checkForNull(quarterQuotes);
        return percentageResult(dayBefore, quarterQuotes, "-0.01", "0.01");
    }

    /**
     * Rule #08
     *
     * @param oneMonthBefore
     * @param actualValue
     * @return
     * @throws NullPointerException if any parameter is null
     */
    public static int earningsRevision(BigDecimal oneMonthBefore, BigDecimal actualValue) {
        checkForNull(oneMonthBefore);
        checkForNull(actualValue);
        return percentageResult(oneMonthBefore, actualValue, "-0.05", "0.05");
    }

    /**
     * Rule #10
     *
     * @param oneYearAgo
     * @param actualValue
     * @return
     */
    public static int quote1YearAgo(BigDecimal oneYearAgo, BigDecimal actualValue) {
        checkForNull(oneYearAgo);
        checkForNull(actualValue);
        return percentageResult(oneYearAgo, actualValue, "-0.05", "0.05");
    }

    private static int percentageResult(BigDecimal pastNo, BigDecimal actualValue, String percentageLow, String percentageHigh) {
        BigDecimal highThreshold = new BigDecimal(percentageHigh);
        BigDecimal lowThreshold = new BigDecimal(percentageLow);
        BigDecimal percent = actualValue.divide(pastNo, mathContext).subtract(BigDecimal.ONE);

        if (percent.compareTo(highThreshold) == 1) {
            return 1;
        } else if (percent.compareTo(lowThreshold) == -1) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * Rule #12
     * Not relevant for Small&Mid-Caps
     *
     * @param quotes
     * @param indexQuotes
     * @param marketCap
     * @return
     * @throws IllegalArgumentException if both list are not of size 4
     * @throws NullPointerException if any parameter is null
     */
    public static int threeMonthReversal(List<BigDecimal> quotes, List<BigDecimal> indexQuotes, BigDecimal marketCap) {
        collectionHasSize(quotes, 4);
        collectionHasSize(indexQuotes, 4);
        checkForNull(marketCap);
        if (!(marketCap.compareTo(fiveMrd) == 1)) {
            return 0;
        }
        boolean threeTimesLess = true;
        boolean threeTimesGreater = true;
        List<BigDecimal> percentageQuote = percentageDifference(quotes);
        List<BigDecimal> percentageIndex = percentageDifference(indexQuotes);

        for (int i = 0; i < 3; i++) {
            if (percentageQuote.get(i).compareTo(percentageIndex.get(i)) >= 0) {
                threeTimesLess = false;
            }
            if (percentageQuote.get(i).compareTo(percentageIndex.get(i)) <= 0) {
                threeTimesGreater = false;
            }
        }
        if (threeTimesGreater) {
            return -1;
        } else if (threeTimesLess) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Creates the difference of a list of quotes.
     * Actual quote / quote before - 1
     *
     * @param quotes
     * @return
     */
    private static List<BigDecimal> percentageDifference(List<BigDecimal> quotes) {
        List<BigDecimal> differenceList = new Vector<>();
        if (quotes.size() == 4) {
            for (int i = 0; i < 3; i++) {
                BigDecimal actual = quotes.get(i + 1);
                BigDecimal before = quotes.get(i);
                BigDecimal percentDifference = actual.divide(before, mathContext);
                percentDifference = percentDifference.subtract(BigDecimal.ONE);
                differenceList.add(percentDifference);
            }
            return differenceList;
        } else {
            throw new IllegalArgumentException("The quotes List should have a size of 4, but is: " + quotes.size());
        }
    }

    /**
     * Rule #13
     *
     * @param epsActual
     * @param epsNext
     * @return
     * @throws NullPointerException for both parameters
     */
    public static int applyEarningsGrowth(BigDecimal epsActual, BigDecimal epsNext) {
        checkForNull(epsActual);
        checkForNull(epsNext);
        BigDecimal percent = epsNext.divide(epsActual, mathContext).subtract(BigDecimal.ONE);
        if (percent.compareTo(EPS_GROWTH_HIGH) == 1) return 1;
        if (percent.compareTo(EPS_GROWTH_LOW) == -1) return -1;
        return 0;
    }

    /**
     * Rule #09
     *
     * @param sixMonthsAgo
     * @param actualValue
     * @return
     */
    public int quote6MonthsAgo(BigDecimal sixMonthsAgo, BigDecimal actualValue) {
        checkForNull(sixMonthsAgo);
        checkForNull(actualValue);
        return percentageResult(sixMonthsAgo, actualValue, "-0.05", "0.05");
    }

    /**
     * Rule #11
     *
     * @param quote6MonthsAgo
     * @param quote1YearAgo
     * @return
     */
    public int momentum(int quote6MonthsAgo, int quote1YearAgo) {
        if (quote6MonthsAgo == 1 && quote1YearAgo < 1) {
            return 1;
        } else if (quote6MonthsAgo == -1 && quote1YearAgo > -1) {
            return -1;
        } else {
            return 0;
        }
    }

}

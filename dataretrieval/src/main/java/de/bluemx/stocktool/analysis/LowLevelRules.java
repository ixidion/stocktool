package de.bluemx.stocktool.analysis;

import de.bluemx.stocktool.model.AnalystsOpinion;

import java.math.BigDecimal;

/**
 * Created by teclis on 23.01.17.
 */
public class LowLevelRules {

    private final BigDecimal ROE_HIGH = new BigDecimal("20.0");
    private final BigDecimal ROE_LOW = new BigDecimal("10.0");
    private final BigDecimal EBITMARGIN_HIGH = new BigDecimal("12.0");
    private final BigDecimal EBITMARGIN_LOW = new BigDecimal("6.0");
    private final BigDecimal EQUITYRATIO_HIGH = new BigDecimal("25.0");
    private final BigDecimal EQUITYRATIO_LOW = new BigDecimal("15.0");
    private final BigDecimal EPS_GROWTH_LHIGH = new BigDecimal("105.0");
    private final BigDecimal EPS_GROWTH_LOW = new BigDecimal("0.95");


    /**
     * Rule #01
     * RoE value of last business year.
     * Normally these figures are available approx. 3 months after business year has ended.
     *
     * @param roeValue
     * @return
     */
    public int applyRoeRule(BigDecimal roeValue) {
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
     */
    public int applyEbitMarginRule(BigDecimal ebitMargin) {
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
     */
    public int applyEquityRatioRule(BigDecimal equityRatio) {
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
     */
    public BigDecimal applyAverageKGV5(BigDecimal yearNMinus3, BigDecimal yearNMinus2, BigDecimal yearNMinus1, BigDecimal year, BigDecimal yearNPlus1, BigDecimal actualQuote) {
        BigDecimal five = new BigDecimal("5.0");
        BigDecimal average = yearNMinus3.add(yearNMinus2).add(yearNMinus1).add(year).add(yearNPlus1).divide(five);
        BigDecimal result = average.divide(actualQuote);
        return result;
    }

    /**
     * Rule #05
     *
     * @param year
     * @param actualQUote
     * @return
     */
    public BigDecimal applyActualPriceEarningsRatio(BigDecimal year, BigDecimal actualQUote) {
        return year.divide(actualQUote);
    }

    public int applyAnalystsOpinion(int analystsCount, AnalystsOpinion analystsOpinion, BigDecimal marketCap) {
        if (analystsCount == 0) {
            throw new IllegalArgumentException("No analysts.");
        } else {
            return 0;//TODO
        }
    }

    /**
     * Rule #13
     *
     * @param epsActual
     * @param epsNext
     * @return
     */
    public int applyEarningsGrowth(BigDecimal epsActual, BigDecimal epsNext) {
        BigDecimal percent = epsNext.divide(epsActual);
        if (percent.compareTo(EQUITYRATIO_HIGH) == 1) return 1;
        if (percent.compareTo(EQUITYRATIO_LOW) == -1) return -1;
        return 0;
    }


}

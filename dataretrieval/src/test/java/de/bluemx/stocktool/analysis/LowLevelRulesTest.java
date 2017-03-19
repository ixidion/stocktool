package de.bluemx.stocktool.analysis;

import de.bluemx.stocktool.model.AnalystsOpinion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by teclis on 26.01.17.
 */
class LowLevelRulesTest {
    private LowLevelRules rules;

    @BeforeEach
    void setUp() {
        rules = new LowLevelRules();

    }

    @Test
    void applyRoeRule1() {
        BigDecimal high = new BigDecimal("21");
        assertEquals(1, rules.applyRoeRule(high));
    }

    @Test
    void applyRoeRule2() {
        BigDecimal middle = new BigDecimal("20");
        assertEquals(0, rules.applyRoeRule(middle));
    }

    @Test
    void applyRoeRule3() {
        BigDecimal low = new BigDecimal("9.99");
        assertEquals(-1, rules.applyRoeRule(low));
    }

    @Test
    void applyEbitMarginRuleFinancial() {
        BigDecimal high = new BigDecimal("13");
        assertEquals(0, rules.applyEbitMarginRule(high, true));
    }

    @Test
    void applyEbitMarginRuleHigh() {
        BigDecimal high = new BigDecimal("13");
        assertEquals(1, rules.applyEbitMarginRule(high, false));
    }

    @Test
    void applyEbitMarginRuleMiddle() {
        BigDecimal middle = new BigDecimal("6");
        assertEquals(0, rules.applyEbitMarginRule(middle, false));
    }

    @Test
    void applyEbitMarginRuleLow() {
        BigDecimal low = new BigDecimal("-1");
        assertEquals(-1, rules.applyEbitMarginRule(low, false));
    }

    @Test
    void applyEquityRatioRuleHigh() {
        BigDecimal high = new BigDecimal("25.1");
        assertEquals(1, rules.applyEquityRatioRule(high, false));
    }

    @Test
    void applyEquityRatioRuleMiddle() {
        BigDecimal middle = new BigDecimal("15");
        assertEquals(0, rules.applyEquityRatioRule(middle, false));
    }

    @Test
    void applyEquityRatioRule() {
        BigDecimal low = new BigDecimal("14.9");
        assertEquals(-1, rules.applyEquityRatioRule(low, false));
    }

    @Test
    void applyEquityRatioFinRuleHigh() {
        BigDecimal high = new BigDecimal("21.1");
        assertEquals(1, rules.applyEquityRatioRule(high, true));
    }

    @Test
    void applyEquityRatioFinRuleMiddle() {
        BigDecimal middle = new BigDecimal("5");
        assertEquals(0, rules.applyEquityRatioRule(middle, true));
    }

    @Test
    void applyEquityRatioFinRule() {
        BigDecimal low = new BigDecimal("4.9");
        assertEquals(-1, rules.applyEquityRatioRule(low, true));
    }

    @Test
    void applyAverageKGV5() {
        BigDecimal one = new BigDecimal("1.0");
        BigDecimal two = new BigDecimal("3.0");
        BigDecimal three = new BigDecimal("4.0");
        BigDecimal four = new BigDecimal("2.0");
        BigDecimal five = new BigDecimal("10.0");
        BigDecimal actualQuote = new BigDecimal("40");
        assertEquals(1, rules.applyAverageKGV5(one, two, three, four, five, actualQuote));
        actualQuote = new BigDecimal("64");
        assertEquals(0, rules.applyAverageKGV5(one, two, three, four, five, actualQuote));
        actualQuote = new BigDecimal("321");
        assertEquals(-1, rules.applyAverageKGV5(one, two, three, four, five, actualQuote));

    }

    @Test
    void applyActualPriceEarningsRatioMiddle() {
        BigDecimal earnings = new BigDecimal("1.0");
        BigDecimal quoteMiddle = new BigDecimal("12.0");
        assertEquals(0, rules.applyActualPriceEarningsRatio(earnings, quoteMiddle));
    }

    @Test
    void applyActualPriceEarningsRatioHigh() {
        BigDecimal earnings = new BigDecimal("1.0");
        BigDecimal quoteHigh = new BigDecimal("16.1");
        assertEquals(-1, rules.applyActualPriceEarningsRatio(earnings, quoteHigh));
    }

    @Test
    void applyActualPriceEarningsRatioLow() {
        BigDecimal earnings = new BigDecimal("1.0");
        BigDecimal quoteLow = new BigDecimal("11.9");
        assertEquals(1, rules.applyActualPriceEarningsRatio(earnings, quoteLow));
    }

    @Test
    void applyAnalystsOpinionZero() {
        assertThrows(IllegalArgumentException.class, () -> rules.applyAnalystsOpinion(0, null, new BigDecimal("5000")));
    }

    @Test
    void applyAnalystsOpinionNoMarketCap() {
        assertThrows(NullPointerException.class, () -> rules.applyAnalystsOpinion(10, AnalystsOpinion.BUY, null));
    }

    @Test
    void applyAnalystsOpinionLargeCap() {
        assertEquals(-1, rules.applyAnalystsOpinion(10, AnalystsOpinion.BUY, new BigDecimal("5001")));
    }

    @Test
    void applyAnalystsOpinionSMCap() {
        assertEquals(1, rules.applyAnalystsOpinion(5, AnalystsOpinion.BUY, new BigDecimal("5000")));
    }

    @Test
    void applyAnalystsOpinionSMCapHighAnlystsCount() {
        assertEquals(1, rules.applyAnalystsOpinion(6, AnalystsOpinion.DECREASE, new BigDecimal("5000")));
    }

    @Test
    void reactionToQuarterQuotesIncrease() {
        BigDecimal dayBefore = new BigDecimal("100");
        BigDecimal actualDay = new BigDecimal("101.1");
        assertEquals(1, rules.reactionToQuarterQuotes(dayBefore, actualDay));
    }

    @Test
    void reactionToQuarterQuotesNoReaction() {
        BigDecimal dayBefore = new BigDecimal("100");
        BigDecimal actualDay = new BigDecimal("101");
        assertEquals(0, rules.reactionToQuarterQuotes(dayBefore, actualDay));
    }

    @Test
    void reactionToQuarterQuotesDecrease() {
        BigDecimal dayBefore = new BigDecimal("100");
        BigDecimal actualDay = new BigDecimal("98.9999");
        assertEquals(-1, rules.reactionToQuarterQuotes(dayBefore, actualDay));
    }

    @Test
    void earningsRevisionHigh() {
        BigDecimal monthBefore = new BigDecimal("100");
        BigDecimal actualDay = new BigDecimal("105.1");
        assertEquals(1, rules.earningsRevision(monthBefore, actualDay));
    }

    @Test
    void earningsRevisionMiddle() {
        BigDecimal monthBefore = new BigDecimal("100");
        BigDecimal actualDay = new BigDecimal("105");
        assertEquals(0, rules.earningsRevision(monthBefore, actualDay));
    }

    @Test
    void earningsRevisionLow() {
        BigDecimal monthBefore = new BigDecimal("100");
        BigDecimal actualDay = new BigDecimal("94.9");
        assertEquals(-1, rules.earningsRevision(monthBefore, actualDay));
    }

    @Test
    void quote6MonthsAgoHigh() {
        BigDecimal sixMonthsBefore = new BigDecimal("100");
        BigDecimal actualDay = new BigDecimal("105.1");
        assertEquals(1, rules.earningsRevision(sixMonthsBefore, actualDay));
    }

    @Test
    void quote6MonthsAgoMiddle() {
        BigDecimal sixMonthsBefore = new BigDecimal("100");
        BigDecimal actualDay = new BigDecimal("105");
        assertEquals(0, rules.earningsRevision(sixMonthsBefore, actualDay));
    }

    @Test
    void quote6MonthsAgoLow() {
        BigDecimal sixMonthsBefore = new BigDecimal("100");
        BigDecimal actualDay = new BigDecimal("94.9");
        assertEquals(-1, rules.earningsRevision(sixMonthsBefore, actualDay));
    }

    @Test
    void quote1YearAgoHigh() {
        BigDecimal yearBefore = new BigDecimal("100");
        BigDecimal actualDay = new BigDecimal("105.1");
        assertEquals(1, rules.earningsRevision(yearBefore, actualDay));
    }

    @Test
    void quote1YearAgoMiddle() {
        BigDecimal yearBefore = new BigDecimal("100");
        BigDecimal actualDay = new BigDecimal("95");
        assertEquals(0, rules.earningsRevision(yearBefore, actualDay));
    }

    @Test
    void quote1YearAgoLow() {
        BigDecimal yearBefore = new BigDecimal("100");
        BigDecimal actualDay = new BigDecimal("94.9999999999");
        assertEquals(-1, rules.earningsRevision(yearBefore, actualDay));
    }

    @Test
    void momentumIncrease() {
        assertEquals(1, rules.momentum(1, 0));
        assertEquals(1, rules.momentum(1, -1));
    }

    @Test
    void momentumHold() {
        assertEquals(0, rules.momentum(1, 1));
        assertEquals(0, rules.momentum(0, 0));
        assertEquals(0, rules.momentum(0, 1));
        assertEquals(0, rules.momentum(0, -1));
        assertEquals(0, rules.momentum(-1, -1));
    }

    @Test
    void momentumDecrease() {
        assertEquals(-1, rules.momentum(-1, 0));
        assertEquals(-1, rules.momentum(-1, 1));
    }

    @Test
    void threeMonthReversalHigh() {
        List<BigDecimal> quote = new Vector<>(4);
        quote.add(new BigDecimal("100"));
        quote.add(new BigDecimal("110"));
        quote.add(new BigDecimal("120"));
        quote.add(new BigDecimal("130"));
        List<BigDecimal> index = new Vector<>(4);
        index.add(new BigDecimal("105"));
        index.add(new BigDecimal("110"));
        index.add(new BigDecimal("115"));
        index.add(new BigDecimal("120"));
        assertEquals(-1, rules.threeMonthReversal(quote, index, new BigDecimal("5001")));

    }

    @Test
    void threeMonthReversalLow() {
        List<BigDecimal> index = new Vector<>(4);
        index.add(new BigDecimal("100"));
        index.add(new BigDecimal("110"));
        index.add(new BigDecimal("120"));
        index.add(new BigDecimal("130"));
        List<BigDecimal> quote = new Vector<>(4);
        quote.add(new BigDecimal("105"));
        quote.add(new BigDecimal("110"));
        quote.add(new BigDecimal("115"));
        quote.add(new BigDecimal("120"));
        assertEquals(1, rules.threeMonthReversal(quote, index, new BigDecimal("5001")));

    }

    @Test
    void threeMonthReversalSMCap() {
        List<BigDecimal> index = new Vector<>(4);
        index.add(new BigDecimal("100"));
        index.add(new BigDecimal("110"));
        index.add(new BigDecimal("120"));
        index.add(new BigDecimal("130"));
        List<BigDecimal> quote = new Vector<>(4);
        quote.add(new BigDecimal("105"));
        quote.add(new BigDecimal("110"));
        quote.add(new BigDecimal("115"));
        quote.add(new BigDecimal("120"));
        assertEquals(0, rules.threeMonthReversal(quote, index, new BigDecimal("5000")));

    }

    @Test
    void threeMonthReversalAverage() {
        List<BigDecimal> index = new Vector<>(4);
        index.add(new BigDecimal("105"));
        index.add(new BigDecimal("110"));
        index.add(new BigDecimal("120"));
        index.add(new BigDecimal("130"));
        List<BigDecimal> quote = new Vector<>(4);
        quote.add(new BigDecimal("105"));
        quote.add(new BigDecimal("110"));
        quote.add(new BigDecimal("115"));
        quote.add(new BigDecimal("120"));
        assertEquals(0, rules.threeMonthReversal(quote, index, new BigDecimal("5001")));

    }

    @Test
    void applyEarningsGrowthHigh() {
        BigDecimal actual = new BigDecimal("100");
        BigDecimal next = new BigDecimal("105.1");
        assertEquals(1, rules.applyEarningsGrowth(actual, next));

    }

    @Test
    void applyEarningsGrowthMiddle() {
        BigDecimal actual = new BigDecimal("100");
        BigDecimal next = new BigDecimal("105");
        assertEquals(0, rules.applyEarningsGrowth(actual, next));

    }

    @Test
    void applyEarningsGrowthLow() {
        BigDecimal actual = new BigDecimal("100");
        BigDecimal next = new BigDecimal("94.9");
        assertEquals(-1, rules.applyEarningsGrowth(actual, next));

    }

}
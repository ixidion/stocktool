package de.bluemx.stocktool.converter;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

/**
 * Created by teclis on 23.12.16.
 */
class QuoteConverterTest {
    @Test
    void convert() {
        String testString = "22.12.2015 5,509 5,509 5,509 5,509 547 28.12.2015 5,301 5,301 5,301 5,301 166";
        QuoteConverter conv = new QuoteConverter();
        Map<LocalDate, BigDecimal> quoteMap = (Map) conv.convert(testString);

    }

}
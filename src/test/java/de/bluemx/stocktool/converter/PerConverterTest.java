package de.bluemx.stocktool.converter;

import static org.hamcrest.collection.IsMapContaining.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Year;
import java.util.Map;

import static org.hamcrest.collection.IsMapContaining.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by plabonte on 15.12.2016.
 */
class PerConverterTest {
    @Test
    void convert() {
        PerConverter conv = new PerConverter();
        Map<Year, BigDecimal> map = (Map<Year, BigDecimal>) conv.convert("2012", "1,3", "2013", "1,5");
        assertThat(map, hasEntry(Year.of(2012), BigDecimal.valueOf(1.3)));
        assertThat(map, hasEntry(Year.of(2013), BigDecimal.valueOf(1.5)));
    }

    @Test
    void convertBadParameter() {
        PerConverter conv = new PerConverter();
        Map<Year, BigDecimal> map = (Map<Year, BigDecimal>) conv.convert("2012e", "1,3", "2013", "1,5");
        assertThat(map, not(hasEntry(Year.of(2012), BigDecimal.valueOf(1.3))));
        assertThat(map, hasEntry(Year.of(2013), BigDecimal.valueOf(1.5)));
    }

}
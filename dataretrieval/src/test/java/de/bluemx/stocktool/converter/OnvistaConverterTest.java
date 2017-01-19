package de.bluemx.stocktool.converter;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Year;
import java.util.SortedMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsMapContaining.hasEntry;

@Disabled
public class OnvistaConverterTest {
    @Test
    void convert() {
        OnvistaTableConverter conv = new OnvistaTableConverter();
        SortedMap<Year, BigDecimal> map = (SortedMap<Year, BigDecimal>) conv.convert("2012", "1,3", "2013", "1,5");
        assertThat(map, hasEntry(Year.of(2012), BigDecimal.valueOf(1.3)));
        assertThat(map, hasEntry(Year.of(2013), BigDecimal.valueOf(1.5)));
    }

    @Test
    void convertBadParameter() {
        OnvistaTableConverter conv = new OnvistaTableConverter();
        SortedMap<Year, BigDecimal> map = (SortedMap<Year, BigDecimal>) conv.convert("2012e", "1,3", "2013", "1,5");
        assertThat(map, hasEntry(Year.of(2012), BigDecimal.valueOf(1.3)));
        assertThat(map, hasEntry(Year.of(2013), BigDecimal.valueOf(1.5)));
    }

}
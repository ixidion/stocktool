package de.bluemx.stocktool.converter;

import de.bluemx.stocktool.model.YearEstimated;

import java.math.BigDecimal;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * This converter is a Conveter to map onvista HTML Table rows to a Map
 * It extracts all entries with Years and saves them in a Map for later use.
 * Also it is saved, if the value is estimated or not, e.g. 2018e inestead of 2018 without 'e'.
 */
public class OnvistaTableConverter implements Conversion {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.uuuu");

    @Override
    public Object convert(String... strings) {
        SortedMap<YearEstimated, BigDecimal> epsMap = new TreeMap<>();
        if (strings != null) {
            if (strings.length > 0 && strings.length % 2 == 0) {
                boolean uneven = true;
                YearEstimated year = null;
                for (String str : strings) {
                    if (uneven) {
                        year = createYearEstimated(str);
                        uneven = false;
                    } else {
                        BigDecimal decimalValue = createValue(str);
                        epsMap.put(year, decimalValue);
                        uneven = true;
                    }
                }
                return epsMap;
            }
            throw new IllegalArgumentException("The no of parameters is not 1 as expected.");
        }
        throw new IllegalArgumentException("The Argument is null.");
    }

    private BigDecimal createValue(String value) {
        if (value.equals("-")) {
            return null;
        } else {
            BigDecimalConverter conv = new BigDecimalConverter();
            BigDecimal decimal = (BigDecimal) conv.convert(value);
            return decimal;
        }
    }

    private YearEstimated createYearEstimated(String year) {
        if (year.length() == 5) {
            Year javaYear = Year.parse(year.substring(0, 4));
            return new YearEstimated(javaYear, true);
        } else {
            Year javaYear = Year.parse(year);
            return new YearEstimated(javaYear, false);
        }


    }
}

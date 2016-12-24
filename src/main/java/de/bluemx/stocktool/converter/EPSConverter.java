package de.bluemx.stocktool.converter;

import de.bluemx.stocktool.model.YearEstimated;

import java.math.BigDecimal;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EPSConverter implements Conversion {
    private final String pattern = "(\\d{4}e?)\\W(\\d{4}e?)\\W(\\d{4}e?)\\W(\\d{4}e?)\\W(\\d{4}e?)\\W(\\d{4}e?)\\W(\\d{4}e?)\\WGewinn pro Aktie in EUR\\W(-|\\d+,\\d+)\\W(-|\\d+,\\d+)\\W(-|\\d+,\\d+)\\W(-|\\d+,\\d+)\\W(-|\\d+,\\d+)\\W(-|\\d+,\\d+)\\W(-|\\d+,\\d+)\\W";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.uuuu");

    @Override
    public Object convert(String... strings) {
        Map<YearEstimated, BigDecimal> epsMap = new LinkedHashMap<>();
        if (strings != null) {
            if (strings.length == 1) {
                Pattern p = Pattern.compile(pattern);
                Matcher m = p.matcher(strings[0]);

                if (m.find() && m.groupCount() == 14) {
                    for (int i = 1; i <= 7; i++) {
                        String year = m.group(i);
                        String value = m.group(i + 7);
                        YearEstimated yearEst = createYearEstimated(year);
                        BigDecimal decimalValue = createValue(value);
                        epsMap.put(yearEst, decimalValue);
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

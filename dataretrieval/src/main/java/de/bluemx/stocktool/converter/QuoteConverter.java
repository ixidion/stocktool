package de.bluemx.stocktool.converter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuoteConverter implements Conversion {
    private final String pattern = "(\\d\\d[.]\\d\\d[.]\\d{4})\\W\\d+,\\d+\\W\\d+,\\d+\\W\\d+,\\d+\\W(\\d+,\\d+)\\W\\d+\\W?";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.uuuu");

    @Override
    public Object convert(String... strings) {
        Map<LocalDate, BigDecimal> quoteMap = new LinkedHashMap<>();
        if (strings != null) {
            if (strings.length == 1) {
                Pattern p = Pattern.compile(pattern);
                Matcher m = p.matcher(strings[0]);

                while (m.find()) {
                    String dateString = m.group(1);
                    String quoteString = m.group(2);
                    LocalDate localDate = LocalDate.parse(dateString, formatter);
                    BigDecimal decimal = (BigDecimal) new BigDecimalConverter().convert(quoteString);
                    quoteMap.put(localDate, decimal);
                }
                return quoteMap;
            }
            throw new IllegalArgumentException("The no of parameters is not 1 as expected.");
        }
        throw new IllegalArgumentException("The Argument is null.");
    }
}

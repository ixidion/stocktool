package de.bluemx.stocktool.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.Year;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PerConverter implements Conversion {
    final static Logger log = LoggerFactory.getLogger(PerConverter.class);
    Map<Year, BigDecimal> perMap = new HashMap<>();
    @Override
    public Object convert(String... strings) {
        if (strings != null) {
            if (strings.length % 2 == 0) {
                for (int i=0; i <strings.length; i=i+2) {
                    Year year = null;
                    BigDecimal decimal = null;
                    // Extract year
                    if (strings[i].matches("\\d{4}.*")) {
                        Pattern pattern = Pattern.compile("(\\d{4}).*");
                        Matcher match = pattern.matcher(strings[i]);
                        String strYear = match.group();
                        int intYear = Integer.parseInt(strYear);
                        year = Year.of(intYear);
                    } else {
                        log.error("Extraction of year '{}' failed.", strings[i]);
                    }

                    // Extract PER-Value
                    if (strings[i+1].matches("\\d+[,.]\\d+")) {
                        String number = strings[i+1].replace(",", ".");
                        decimal = new BigDecimal(number);
                    } else {
                        log.error("Extraction of value '{}' failed.", strings[i+1]);
                    }
                    if (year != null && decimal != null) {
                        perMap.put(year, decimal);
                    }
                }
                return perMap;
            }
            throw new IllegalArgumentException("Parameters are missing. Uneven No. detected.");
        }
        throw new IllegalArgumentException("The Argument is null.");
    }

}

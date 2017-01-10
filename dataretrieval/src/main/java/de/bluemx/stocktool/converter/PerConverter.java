package de.bluemx.stocktool.converter;

import de.bluemx.stocktool.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.Year;
import java.util.SortedMap;
import java.util.TreeMap;

public class PerConverter implements Conversion {
    final static Logger log = LoggerFactory.getLogger(PerConverter.class);
    final static String digitPattern = "(\\d{4})";
    final static String bigdecimalPattern = "\\d+[,.]\\d+";
    SortedMap<Year, BigDecimal> perMap = new TreeMap<>();
    private StringUtil stringUtil;

    public PerConverter() {
        stringUtil = new StringUtil();
    }

    @Override
    public Object convert(String... strings) {
        if (strings != null) {
            if (strings.length % 2 == 0) {
                for (int i=0; i <strings.length; i=i+2) {
                    Year year = null;
                    BigDecimal decimal = null;
                    // Extract year

                    String extractedYear = stringUtil.extractPatternFromString(strings[i], digitPattern);

                    if (extractedYear != null) {
                        int intYear = Integer.parseInt(extractedYear);
                        year = Year.of(intYear);
                    } else {
                        log.error("Extraction of year '{}' failed.", strings[i]);
                    }

                    // Extract PER-Value
                    if (strings[i+1].matches(bigdecimalPattern)) {
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

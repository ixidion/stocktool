
package de.bluemx.stocktool.converter;

import de.bluemx.stocktool.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class BigDecimalConverter implements Conversion {
    final static Logger log = LoggerFactory.getLogger(de.bluemx.stocktool.converter.BigDecimalConverter.class);
    final static String bigdecimalPattern = "([+-]?\\d+[,.]\\d+)";

    private StringUtil stringUtil;

    public BigDecimalConverter() {
        this.stringUtil = new StringUtil();
    }

    @Override
    public Object convert(String... strings) {
        if (strings != null) {
            if (strings.length == 1) {
                String str = stringUtil.extractPatternFromString(strings[0], bigdecimalPattern);
                if (str != null) {
                    String number = str.replace(",", ".");
                    return new BigDecimal(number);
                } else {
                    log.error("Extraction of value '{}' failed.", strings[0]);
                }
            }
            throw new IllegalArgumentException("The Argument length is not 1.");
        }
        throw new IllegalArgumentException("The Argument is null.");
    }


}



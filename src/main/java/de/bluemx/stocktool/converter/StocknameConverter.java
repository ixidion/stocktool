package de.bluemx.stocktool.converter;

import de.bluemx.stocktool.helper.StringUtil;

public class StocknameConverter implements Conversion {
    final String REGEXP = "(.*)\\sAktie";

    @Override
    public Object convert(String... strings) {
        if (strings != null) {
            if (strings.length == 1) {
                String returnStr = StringUtil.extractPatternFromString(strings[0], REGEXP);
                return returnStr;
            }
            throw new IllegalArgumentException("The no of parameters is not 1 as expected.");
        }
        throw new IllegalArgumentException("The Argument is null.");
    }
}

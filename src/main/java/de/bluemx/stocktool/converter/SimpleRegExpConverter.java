package de.bluemx.stocktool.converter;

import de.bluemx.stocktool.helper.StringUtil;

public class SimpleRegExpConverter implements Conversion {

    @Override
    public Object convert(String... strings) {
        if (strings != null) {
            if (strings.length == 2) {
                String returnStr = StringUtil.extractPatternFromString(strings[0], strings[1]);
                return returnStr;
            }
            throw new IllegalArgumentException("The no of parameters is not 1 as expected.");
        }
        throw new IllegalArgumentException("The Argument is null.");
    }
}

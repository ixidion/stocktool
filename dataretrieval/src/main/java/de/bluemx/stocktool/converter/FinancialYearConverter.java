package de.bluemx.stocktool.converter;

import de.bluemx.stocktool.helper.StringUtil;

import java.time.LocalDate;

public class FinancialYearConverter implements Conversion {
    final String REGEXP = "(\\d{2})[.](\\d{2})[.]";

    @Override
    public Object convert(String... strings) {
        if (strings != null) {
            if (strings.length == 1) {
                String[] returnStr = StringUtil.extractPatternGroupsFromString(strings[0], REGEXP);
                LocalDate localDate = LocalDate.of(1, Integer.parseInt(returnStr[1]), Integer.parseInt(returnStr[0]));
                return localDate;
            }
            throw new IllegalArgumentException("The no of parameters is not 1 as expected.");
        }
        throw new IllegalArgumentException("The Argument is null.");
    }
}

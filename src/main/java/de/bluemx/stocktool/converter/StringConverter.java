package de.bluemx.stocktool.converter;

public class StringConverter implements Conversion {
    @Override
    public Object convert(String... strings) {
        if (strings != null) {
            if (strings.length == 1) {
                return strings[0];
            }
            throw new IllegalArgumentException("The no of parameters is not 1 as expected.");
        }
        throw new IllegalArgumentException("The Argument is null.");
    }
}

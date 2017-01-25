package de.bluemx.stocktool.converter;

public class SectorConverter implements Conversion {
    @Override
    public Object convert(String... strings) {
        if (strings != null) {
            if (strings.length == 1) {
                if (strings[0].contains("Finanzsektor")) {
                    return new Boolean(true);
                }
                return new Boolean(false);
            }
            throw new IllegalArgumentException("The no of parameters is not 1 as expected.");
        }
        throw new IllegalArgumentException("The Argument is null.");
    }
}

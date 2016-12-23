package de.bluemx.stocktool.converter;

import de.bluemx.stocktool.model.AnalystsOpinion;

public class AnalystsConverter implements Conversion {
    @Override
    public Object convert(String... strings) {
        if (strings != null) {
            if (strings.length == 1) {
                AnalystsOpinion returnValue;
                switch (strings[0]) {
                    case "KAUFEN":
                        returnValue = AnalystsOpinion.BUY;
                        break;
                    case "AUFSTOCKEN":
                        returnValue = AnalystsOpinion.INCREASE;
                        break;
                    case "HALTEN":
                        returnValue = AnalystsOpinion.HOLD;
                        break;
                    case "REDUZIEREN":
                        returnValue = AnalystsOpinion.DECREASE;
                        break;
                    case "VERKAUFEN":
                        returnValue = AnalystsOpinion.SELL;
                        break;
                    default:
                        returnValue = null;
                }
                return returnValue;
            }
            throw new IllegalArgumentException("The no of parameters is not 1 as expected.");
        }
        throw new IllegalArgumentException("The Argument is null.");
    }
}

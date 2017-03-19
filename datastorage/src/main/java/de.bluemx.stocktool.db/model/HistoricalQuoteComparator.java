package de.bluemx.stocktool.db.model;

import java.util.Comparator;

public class HistoricalQuoteComparator implements Comparator<HistoricalQuote> {

    @Override
    public int compare(HistoricalQuote o1, HistoricalQuote o2) {
        return o1.getQuoteDate().compareTo(o2.getQuoteDate());
    }
}

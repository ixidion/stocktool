package de.bluemx.stocktool.fetch;

import de.bluemx.stocktool.model.StockQuoteData;

import javax.cache.Cache;

/**
 * Created by teclis on 09.12.16.
 */
public interface Fetch {
    StockQuoteData fetch(String isin);

}
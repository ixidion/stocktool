package de.bluemx.stocktool.fetch;

import com.google.inject.Inject;
import de.bluemx.stocktool.helper.Config;
import de.bluemx.stocktool.model.StockQuoteData;

import javax.cache.Cache;

/**
 * Created by teclis on 09.12.16.
 */
public class FetchOnvistaFundamental implements Fetch {
    private Config config;

    @Inject
    public FetchOnvistaFundamental(Config config) {
        this.config = config;
    }


    @Override
    public StockQuoteData fetch(String isin) {

        return null;
    }

    @Override
    public StockQuoteData fetch(String isin, Cache<String, String> cache) {
        return null;
    }
}

package de.bluemx.stocktool.fetch;

import com.google.inject.Inject;
import de.bluemx.stocktool.model.StockQuoteData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class IsinFetcher {
    final static Logger log = LoggerFactory.getLogger(IsinFetcher.class);

    private GenericFetcher<StockQuoteData> genericFetcher;

    @Inject
    public IsinFetcher(GenericFetcher<StockQuoteData> genericFetcher) {
        this.genericFetcher = genericFetcher;
    }

    public StockQuoteData populateByIsin(String isin) {
        StockQuoteData stock = new StockQuoteData(isin);
        return genericFetcher.process(stock);
    }

}

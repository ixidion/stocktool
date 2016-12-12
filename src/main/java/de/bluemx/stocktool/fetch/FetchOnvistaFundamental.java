package de.bluemx.stocktool.fetch;

import com.google.inject.Inject;
import de.bluemx.stocktool.cache.Cacheprovider;
import de.bluemx.stocktool.helper.Config;
import de.bluemx.stocktool.model.StockQuoteData;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.cache.Cache;
import java.io.IOException;
/**
 * Created by teclis on 09.12.16.
 */
public class FetchOnvistaFundamental implements Fetch {
    final static Logger log = LoggerFactory.getLogger(FetchOnvistaFundamental.class);
    public static final String ONVISTA_PREFIX = "ONVISTA";
    private String cacheOnvistaIsin;

    private Config config;
    private Cache<String, String> cache;

    @Inject
    public FetchOnvistaFundamental(Config config, Cacheprovider cacheprovider) {
        this.config = config;
        this.cache = cacheprovider.getCache();
    }


    @Override
    public StockQuoteData fetch(String isin) {
        setOnvistaCacheEntry(isin);
        String foundUrl = getRealUrl(isin);
        String[] splittedUrl = foundUrl.split("/");
        System.out.println(splittedUrl[splittedUrl.length-1]);

        return null;

    }

    private String getRealUrl(String isin) {
        if (!cache.containsKey("ONVISTA" + isin)) {
            String searchUrl = (String) config.getConfig().get("onvista.searchurl");
            String isinUrl = String.format(searchUrl, isin);
            try {
                Connection con = Jsoup.connect(isinUrl)
                        .userAgent("Mozilla")
                        .timeout(10000);
                con.get();
//                log.debug(con.response().url().toString());
                return con.response().url().toString();
            } catch (IOException e) {
                log.error("ISIN not fetched: {} ", isin);
                throw new RuntimeException(e);
            }
        } else {
            return cache.get(cacheOnvistaIsin);
        }
    }

    private void setOnvistaCacheEntry(String isin) {
        cacheOnvistaIsin = ONVISTA_PREFIX + isin;
    }
}

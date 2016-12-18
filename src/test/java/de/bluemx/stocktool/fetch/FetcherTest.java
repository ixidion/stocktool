package de.bluemx.stocktool.fetch;

import de.bluemx.stocktool.helper.ReflectionUtil;
import de.bluemx.stocktool.helper.StringUtil;
import de.bluemx.stocktool.model.StockQuoteData;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FetcherTest {

    static String searchUrl = "http://www.onvista.de/suche/?searchValue=DE000A1K0409";
    static String returnUrl = "http://www.onvista.de/aktien/PFERDEWETTEN-DE-AG-Aktie-DE000A1K0409";

    Map<String, String> urlToFile = new HashMap<>();
    Fetcher fetcher;

    {
        urlToFile.put("http://www.onvista.de/suche/?searchValue=DE000A1K0409", "http://www.onvista.de/aktien/PFERDEWETTEN-DE-AG-Aktie-DE000A1K0409");
        urlToFile.put("http://www.onvista.de/aktien/PFERDEWETTEN-DE-AG-Aktie-DE000A1K0409", "/onvista_test.htm");
    }

    @BeforeEach
    void beforeEach() throws IOException {
        JsoupWrapper jsoup = mock(JsoupWrapper.class);
        for (Map.Entry<String, String> entry : urlToFile.entrySet()) {
            DummyResponse response = mock(DummyResponse.class);
            Connection con = mock(Connection.class);
            when(con.response()).thenReturn(response);
            when(con.userAgent(any())).thenReturn(con);
            when(con.timeout(anyInt())).thenReturn(con);
            if (!entry.getKey().equals("http://www.onvista.de/suche/?searchValue=DE000A1K0409")) {
                when(response.url()).thenReturn(new URL(entry.getKey()));
                // File Access
                InputStream is = this.getClass().getResourceAsStream(entry.getValue());
                when(con.get()).thenReturn(Jsoup.parse(is, null, entry.getKey()));
            } else {
                when(response.url()).thenReturn(new URL(entry.getValue()));
            }
            when(jsoup.connect(entry.getKey())).thenReturn(con);
        }


        UrlFetcher urlFetcher = new UrlFetcher(jsoup, new StringUtil());
        GenericFetcher<StockQuoteData> genericFetcher = new GenericFetcher<>(urlFetcher, new ReflectionUtil(), new StringUtil());
        fetcher = new Fetcher(genericFetcher);
    }

    @Test
    void fetch() {
        StockQuoteData stock = fetcher.populateByIsin("DE000A1K0409");

    }


}
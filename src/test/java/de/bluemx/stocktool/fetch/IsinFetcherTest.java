package de.bluemx.stocktool.fetch;

import de.bluemx.stocktool.model.TestData1;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class IsinFetcherTest {

    static String searchUrl = "http://www.onvista.de/suche/?searchValue=DE000A1K0409";
    static String returnUrl = "http://www.onvista.de/aktien/PFERDEWETTEN-DE-AG-Aktie-DE000A1K0409";

    Map<String, String> urlToFile = new HashMap<>();
    GenericFetcher<TestData1> testdata1Fetcher;

    {
        urlToFile.put("http://www.onvista.de/suche/?searchValue=DE000A1K0409", "http://www.onvista.de/aktien/PFERDEWETTEN-DE-AG-Aktie-DE000A1K0409");
        urlToFile.put("http://www.onvista.de/aktien/PFERDEWETTEN-DE-AG-Aktie-DE000A1K0409", "/onvista_test.htm");
        urlToFile.put("http://www.onvista.de/aktien/fundamental/PFERDEWETTEN-DE-AG-Aktie-DE000A1K0409", "/onvista_test_fundamental.htm");
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
            if (!entry.getKey().equals(searchUrl)) {
                when(response.url()).thenReturn(new URL(entry.getKey()));
                // File Access
                InputStream is = this.getClass().getResourceAsStream(entry.getValue());
                when(con.get()).thenReturn(Jsoup.parse(is, null, entry.getKey()));
            } else {
                when(response.url()).thenReturn(new URL(entry.getValue()));
            }
            when(jsoup.connect(entry.getKey())).thenReturn(con);
        }


        UrlFetcher urlFetcher = new UrlFetcher(jsoup);
        testdata1Fetcher = new GenericFetcher<>(urlFetcher);
    }

    @Test
    void fetch() {
        TestData1 testdata = new TestData1("DE000A1K0409");
        testdata = testdata1Fetcher.process(testdata);
        assertEquals("EMH1", testdata.getSymbol());
        assertEquals("PFERDEWETTEN.DE AG Aktie", testdata.getStockname());
        assertEquals("PFERDEWETTEN.DE AG Aktie", testdata.getStockname());

    }


}
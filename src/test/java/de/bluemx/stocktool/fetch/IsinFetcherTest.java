package de.bluemx.stocktool.fetch;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.bluemx.stocktool.cache.Cacheprovider;
import de.bluemx.stocktool.helper.DefaultInject;
import de.bluemx.stocktool.model.Index;
import de.bluemx.stocktool.model.StockQuoteData;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsMapContaining.hasValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class IsinFetcherTest {

    private static String searchUrl = "http://www.onvista.de/suche/?searchValue=DE000A1K0409";
    private static String searchUrl4Traders = "http://de.4-traders.com/indexbasegauche.php?mots=DE000A1K0409";

    private Map<String, String> urlToFile = new HashMap<>();
    private GenericFetcher<StockQuoteData> stockquoteFetcher;
    private Injector injector;

    {
        LocalDate minusOneYear = LocalDate.now().minusYears(1).minusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.uuuu");
        String year = minusOneYear.format(formatter);
        String urlQuotes = "http://www.onvista.de/onvista/times+sales/popup/historische-kurse/?notationId=54094105&dateStart=%s&interval=Y1&assetName=%%20&exchange=Xetra";
        urlQuotes = String.format(urlQuotes, year);
        urlToFile.put(urlQuotes, "/onvista_kurse.htm");
        urlToFile.put("http://www.onvista.de/suche/?searchValue=DE000A1K0409", "http://www.onvista.de/aktien/PFERDEWETTEN-DE-AG-Aktie-DE000A1K0409");
        urlToFile.put("http://www.onvista.de/aktien/PFERDEWETTEN-DE-AG-Aktie-DE000A1K0409", "/onvista_test.htm");
        urlToFile.put("http://www.onvista.de/aktien/fundamental/PFERDEWETTEN-DE-AG-Aktie-DE000A1K0409", "/onvista_test_fundamental.htm");
        urlToFile.put("http://de.4-traders.com/indexbasegauche.php?mots=DE000A1K0409", "http://de.4-traders.com/PFERDEWETTEN-DE-AG-23145623/");
        urlToFile.put("http://de.4-traders.com/PFERDEWETTEN-DE-AG-23145623/analystenerwartungen/", "/4traders-analysts.htm");
        urlToFile.put("http://www.onvista.de/aktien/times+sales/PFERDEWETTEN-DE-AG-Aktie-DE000A1K0409", "/onvista_times_sales.htm");
    }

    @BeforeEach
    void beforeEach() throws IOException {
        injector = Guice.createInjector(new DefaultInject());
        Cacheprovider cacheprovider = injector.getInstance(Cacheprovider.class);


        JsoupWrapper jsoup = mock(JsoupWrapper.class);
        for (Map.Entry<String, String> entry : urlToFile.entrySet()) {
            DummyResponse response = mock(DummyResponse.class);
            Connection con = mock(Connection.class);
            when(con.response()).thenReturn(response);
            when(con.userAgent(any())).thenReturn(con);
            when(con.timeout(anyInt())).thenReturn(con);
            if (!entry.getKey().equals(searchUrl) && !entry.getKey().equals(searchUrl4Traders)) {
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
        stockquoteFetcher = new GenericFetcher<>(urlFetcher);
    }


    /**
     * A separate Fetcher for Stockquote data, because it can change over time.
     * The testdata Fetcher is more experimental.
     */
    @Test
    void fetchStockquoteData() {
        StockQuoteData testdata = new StockQuoteData("DE000A1K0409", Index.SDAX);
        testdata = stockquoteFetcher.process(testdata);
        assertEquals("EMH1", testdata.getSymbol());
        assertEquals("PFERDEWETTEN.DE AG", testdata.getStockname());
        assertThat(testdata.getUrlParts(), hasValue("PFERDEWETTEN-DE-AG-Aktie-DE000A1K0409"));
        assertThat(testdata.getUrlParts(), hasValue("PFERDEWETTEN-DE-AG-23145623"));
        assertEquals(new BigDecimal("34.17"), testdata.getRoe());
        assertEquals(new BigDecimal("28.35"), testdata.getEbitMargin());
        assertEquals(new BigDecimal("73.83"), testdata.getEquityRatio());
        assertThat(testdata.getHistoryParts(), hasValue("54094105"));
    }

    @Test
    @Disabled
    void realFetchTest() {
        IsinFetcher fetcher = injector.getInstance(IsinFetcher.class);
        StockQuoteData stock = fetcher.populateByIsin("DE000A1K0409", Index.SDAX);
    }


}
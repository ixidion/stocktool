package de.bluemx.stocktool.fetch;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.bluemx.stocktool.helper.DefaultInject;
import de.bluemx.stocktool.model.StockQuoteData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


class FetcherTest {
    @Test
    void fetch1() {

    }

    private static Injector injector;

    @BeforeAll
    static void beforeAll() {
        injector = Guice.createInjector(new DefaultInject());
    }

    @Test
    void fetch() {
        Fetcher fetcher = new Fetcher();
        StockQuoteData stock = fetcher.fetch("1236136");

    }


}
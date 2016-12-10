package de.bluemx.stocktool.fetch;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.Test;

/**
 * Created by teclis on 10.12.16.
 */
class FetchOnvistaFundamentalTest {
    @Test
    void fetch() {
        Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });


        FetchOnvistaFundamental onvista = injector.getInstance(FetchOnvistaFundamental.class);
    }

    @Test
    void fetch1() {

    }

}
package de.bluemx.stocktool.fetch;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import de.bluemx.stocktool.helper.DefaultInject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.cache.Cache;


class FetchOnvistaFundamentalTest {

    private static Injector injector;

    @BeforeAll
    static void beforeAll() {
        injector = Guice.createInjector(new DefaultInject());
    }

    @Test
    void fetch() {
        FetchOnvistaFundamental onvista = injector.getInstance(FetchOnvistaFundamental.class);
        onvista.fetch("DE000A1K0409");
    }


}
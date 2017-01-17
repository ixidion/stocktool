package de.bluemx.stocktool.helper;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class GlobalGuiceInjector {
    private static GlobalGuiceInjector ourInstance = new GlobalGuiceInjector();
    private Injector injector;

    private GlobalGuiceInjector() {
        this.injector = Guice.createInjector(new DefaultInject());
    }

    public static GlobalGuiceInjector getInstance() {
        return ourInstance;
    }

    public static Injector getInjector() {
        return getInstance().injector;
    }
}

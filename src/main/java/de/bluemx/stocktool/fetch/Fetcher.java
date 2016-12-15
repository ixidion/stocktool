package de.bluemx.stocktool.fetch;

import de.bluemx.stocktool.annotations.*;
import de.bluemx.stocktool.model.StockQuoteData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Map;

import static javafx.scene.input.KeyCode.T;


public class Fetcher {
    final static Logger log = LoggerFactory.getLogger(Fetcher.class);
    private Provider[] providers;

    public Fetcher() {
        saveClassAnnotations();
    }


    public StockQuoteData fetch(String isin) {
        StockQuoteData stock = new StockQuoteData(isin);
        return populateStockdata(stock);
    }

    private StockQuoteData populateStockdata(StockQuoteData stock) {
        Field[] fields = StockQuoteData.class.getDeclaredFields();

        for(Field field : fields) {
            recursiveFetch(field, stock);
        }

        return stock;
    }

    private void recursiveFetch(Field field, StockQuoteData stock) {
        Resolvers resolvers = field.getAnnotation(Resolvers.class);
        if (resolvers instanceof Resolvers) {
            for (Resolver resolver : resolvers.value()) {
                String providername = resolver.name();
                Provider provider = getProviderByName(providername);
                if (!provider.required().equals("")) {
                    Field newField = getFieldByName(provider.required());
                    recursiveFetch(newField, stock);
                }
                Object obj = fetchValue(resolver, provider, stock);
                try {
                    field.set(stock, obj);
                    break;
                } catch (IllegalAccessException e) {
                    log.error("Error during access to Field", e);
                }
            }
        }
    }

    private Object fetchValue(Resolver resolver, Provider provider, StockQuoteData stock) {
        return "newValue";
    }

    private Field getFieldByName(String required) {
        Field[] fields = StockQuoteData.class.getFields();
        for (Field field : fields) {
            if (field.getName().equals(required)) return field;
        }
        log.error("Fieldname {} not found", required);
        throw new RuntimeException("Fieldname not found.");
    }

    private <T> T resolveWith(Resolvers resolvers, Class<T> clazz, StockQuoteData stock, Field field) {
        return null;
    }

    private Provider getProviderByName(String name) {
        for (Provider provider : this.providers) {
            if (provider != null) {
                if (provider.name().equals(name)) return provider;
            }
        }
        log.error("Providername {} not found", name);
        throw new RuntimeException("Providername not found.");
    }

    private void saveClassAnnotations() {
        Class stockClass = StockQuoteData.class;

        Annotation[] stockClassAnnotations = stockClass.getAnnotations();

        for (Annotation annotation : stockClassAnnotations) {
            if (annotation instanceof Config) {
                Config config = (Config) annotation;
                this.providers = config.providers();
            }
        }
    }
}

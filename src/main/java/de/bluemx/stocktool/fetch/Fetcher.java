package de.bluemx.stocktool.fetch;

import de.bluemx.stocktool.annotations.*;
import de.bluemx.stocktool.helper.ReflectionUtil;
import de.bluemx.stocktool.model.StockQuoteData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static de.bluemx.stocktool.helper.ReflectionUtil.*;


public class Fetcher {
    final static Logger log = LoggerFactory.getLogger(Fetcher.class);

    public Fetcher() {
    }


    public StockQuoteData populateByIsin(String isin) {
        StockQuoteData stock = new StockQuoteData(isin);
        GenericFetcher<StockQuoteData> genFetcher = new GenericFetcher<>();
        return genFetcher.process(stock);
    }


}

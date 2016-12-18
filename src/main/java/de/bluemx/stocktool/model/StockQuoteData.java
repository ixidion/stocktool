package de.bluemx.stocktool.model;


import de.bluemx.stocktool.annotations.*;
import de.bluemx.stocktool.converter.BigDecimalConverter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.Map;

@Config(providers = {
        @Provider(name = "onvista-basic", dataprovider = Dataprovider.ONVISTA, url = "http://www.onvista.de/aktien/{urlPart}",
        variables={@Variable(key="urlPart", source = "urlParts")},
        required="urlParts"),
        @Provider(name="onvista-isin-search", dataprovider = Dataprovider.ONVISTA, url="http://www.onvista.de/suche/?searchValue={isin}",
                variables = {@Variable(key = "isin", source = "isin")}),
        @Provider(name = "onvista-fundamental", dataprovider = Dataprovider.ONVISTA, url = "http://www.onvista.de/aktien/fundamental/{urlPart}",
                variables = {@Variable(key = "urlPart", source = "urlParts")},
                required = "urlParts")})
public class StockQuoteData {
    @Resolvers({@Resolver(provider = "onvista-basic",
            extractors = {@Extract(searchType = SearchType.XPath, expression = "a.INSTRUMENT")},
            source = Source.RESPONSE)})
    private String stockname = "";

    private String isin;

    @Resolvers({@Resolver(provider ="onvista-isin-search",
            source = Source.URL,
            extractors = {@Extract(searchType = SearchType.REGEXP, expression = "^.*/(.*)")})})
    @ProviderMap
    private Map<Dataprovider,String> urlParts;

//    @Resolvers({@Resolver(provider = "onvista-basic",
//            extractors = {@Extract(searchType = SearchType.REGEXP, expression = "//adasd[]3234")},
//            source = Source.RESPONSE,
//            converterClass = StringConverter.class)})
    private Map<Dataprovider,String> historyParts;


    @Resolvers({@Resolver(provider = "onvista-basic",
            extractors = {@Extract(searchType = SearchType.XPath, expression = "div.WERTPAPIER_DETAILS > dl:nth-of-type(2) > dd")},
            source = Source.RESPONSE)})
    private String symbol;
    private LocalDate fetchDate = LocalDate.now();

    // Return of Equity
    // No 1
    @Resolvers({@Resolver(provider = "onvista-fundamental",
            extractors = {@Extract(searchType = SearchType.XPath, expression = "article.KENNZAHLEN table:nth-of-type(8) tbody tr:nth-of-type(4) td:nth-of-type(5)")},
            source = Source.RESPONSE,
            converterClass = BigDecimalConverter.class,
            validators = {@Validate(expression = "article.KENNZAHLEN table th.ZAHL", expected = "2018e"),
                    @Validate(expression = "article.KENNZAHLEN table:nth-of-type(8) tbody tr:nth-of-type(4) td.INFOTEXT", expected = "Eigenkapitalrendite")})})
    private BigDecimal roe;

    // EBIT-Margin
    // No 2
    @Resolvers({@Resolver(provider = "onvista-fundamental",
            extractors = {@Extract(searchType = SearchType.XPath, expression = "article.KENNZAHLEN table:nth-of-type(8) tbody tr:nth-of-type(2) td:nth-of-type(5)")},
            source = Source.RESPONSE,
            converterClass = BigDecimalConverter.class,
            validators = {@Validate(expression = "article.KENNZAHLEN table th.ZAHL", expected = "2018e"),
                    @Validate(expression = "article.KENNZAHLEN table:nth-of-type(8) tbody tr:nth-of-type(2) td.INFOTEXT", expected = "EBIT-Marge")})})
    private BigDecimal ebitMargin;

    // Equity Ratio
    // No 3
    @Resolvers({@Resolver(provider = "onvista-fundamental",
            extractors = {@Extract(searchType = SearchType.XPath, expression = "article.KENNZAHLEN table:nth-of-type(6) tbody tr:nth-of-type(2) td:nth-of-type(5)")},
            source = Source.RESPONSE,
            converterClass = BigDecimalConverter.class,
            validators = {@Validate(expression = "article.KENNZAHLEN table th.ZAHL", expected = "2018e"),
                    @Validate(expression = "article.KENNZAHLEN table:nth-of-type(6) tbody tr:nth-of-type(2) td.INFOTEXT", expected = "Eigenkapitalquote")})})
    private BigDecimal equityRatio;

    // Price Earnings Ratio / KGV
    // No 4 (Basis)
//    @Resolvers({@Resolver(provider = "onvista-basic",
//            extractors = {@Extract(searchType = SearchType.REGEXP, expression = "//adasd[]3234")},
//            source = Source.RESPONSE,
//            converterClass = PerConverter.class)})
    private Map<Year, String> per;

    // PER actual
    // No 5

    // Analysts
    // No 6
    private String analystsOpinion;
    private String analystsCount;

    private String quote;

    // Reaction Quarter Quote
    // No  7
    private String[] quarterQuote;
    private String[]  quarterQuoteIndex;

    // Earnings Revision
    // No 8
    private String earningsRevision;

    // Quote half year ago
    // No 9
    private String stockPriceHalfYear;

    // Quote year ago
    // No 10 (Basis)
    private String stockPriceYear;

    // Quote Momentum / Kursmomentum
    // No 11
    // Abbilden in Excel

    // Dreimonatsreversal
    // No 12
    private String[] threeMonthReversal;

    // Earnigs per Share last year
    // No 13 (Basis)
    private String epsLY;

    // Earnigs per Share Actual Year
    private String epsAY;

    public StockQuoteData(String isin) {
        this.isin = isin;
    }

}

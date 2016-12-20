package de.bluemx.stocktool.model;


import de.bluemx.stocktool.annotations.*;
import de.bluemx.stocktool.converter.BigDecimalConverter;
import de.bluemx.stocktool.converter.FinancialYearConverter;
import de.bluemx.stocktool.converter.PerConverter;
import de.bluemx.stocktool.converter.StocknameConverter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.Map;

@Config(providers = {
        @Provider(name = "onvista-basic", dataprovider = Dataprovider.ONVISTA, url = "http://www.onvista.de/aktien/{urlPart}",
                variables = {@Variable(key = "urlPart", source = "urlParts")},
                required = "urlParts"),
        @Provider(name = "onvista-isin-search", dataprovider = Dataprovider.ONVISTA, url = "http://www.onvista.de/suche/?searchValue={isin}",
                variables = {@Variable(key = "isin", source = "isin")}),
        @Provider(name = "onvista-fundamental", dataprovider = Dataprovider.ONVISTA, url = "http://www.onvista.de/aktien/fundamental/{urlPart}",
                variables = {@Variable(key = "urlPart", source = "urlParts")},
                required = "urlParts")})
public class StockQuoteData {
    @Resolvers({@Resolver(provider = "onvista-basic",
            extractors = {@Extract(searchType = SearchType.Selector, expression = "a.INSTRUMENT")},
            source = Source.RESPONSE,
            converterClass = StocknameConverter.class)})
    private String stockname = "";

    private String isin;

    @Resolvers({@Resolver(provider = "onvista-isin-search",
            source = Source.URL,
            extractors = {@Extract(searchType = SearchType.REGEXP, expression = "^.*/(.*)")})})
    @ProviderMap
    private Map<Dataprovider, String> urlParts;

    //    @Resolvers({@Resolver(provider = "onvista-basic",
//            extractors = {@Extract(searchType = SearchType.REGEXP, expression = "//adasd[]3234")},
//            source = Source.RESPONSE,
//            converterClass = StringConverter.class)})
    private Map<Dataprovider, String> historyParts;


    @Resolvers({@Resolver(provider = "onvista-basic",
            extractors = {@Extract(searchType = SearchType.Selector, expression = "div.WERTPAPIER_DETAILS > dl:nth-of-type(2) > dd")},
            source = Source.RESPONSE)})
    private String symbol;
    private LocalDate fetchDate = LocalDate.now();

    // Return of Equity
    // No 1
    @Resolvers({@Resolver(provider = "onvista-fundamental",
            extractors = {@Extract(searchType = SearchType.Selector, expression = "article.KENNZAHLEN table:nth-of-type(8) tbody tr:nth-of-type(4) td:nth-of-type(5)")},
            source = Source.RESPONSE,
            converterClass = BigDecimalConverter.class,
            validators = {@Validate(expression = "article.KENNZAHLEN table th.ZAHL", expected = "2018e"),
                    @Validate(expression = "article.KENNZAHLEN table:nth-of-type(8) tbody tr:nth-of-type(4) td.INFOTEXT", expected = "Eigenkapitalrendite")})})
    private BigDecimal roe;

    // EBIT-Margin
    // No 2
    @Resolvers({@Resolver(provider = "onvista-fundamental",
            extractors = {@Extract(searchType = SearchType.Selector, expression = "article.KENNZAHLEN table:nth-of-type(8) tbody tr:nth-of-type(2) td:nth-of-type(5)")},
            source = Source.RESPONSE,
            converterClass = BigDecimalConverter.class,
            validators = {@Validate(expression = "article.KENNZAHLEN table th.ZAHL", expected = "2018e"),
                    @Validate(expression = "article.KENNZAHLEN table:nth-of-type(8) tbody tr:nth-of-type(2) td.INFOTEXT", expected = "EBIT-Marge")})})
    private BigDecimal ebitMargin;

    // Equity Ratio
    // No 3
    @Resolvers({@Resolver(provider = "onvista-fundamental",
            extractors = {@Extract(searchType = SearchType.Selector, expression = "article.KENNZAHLEN table:nth-of-type(6) tbody tr:nth-of-type(2) td:nth-of-type(5)")},
            source = Source.RESPONSE,
            converterClass = BigDecimalConverter.class,
            validators = {@Validate(expression = "article.KENNZAHLEN table th.ZAHL", expected = "2018e"),
                    @Validate(expression = "article.KENNZAHLEN table:nth-of-type(6) tbody tr:nth-of-type(2) td.INFOTEXT", expected = "Eigenkapitalquote")})})
    private BigDecimal equityRatio;

    // Price Earnings Ratio / KGV
    // No 4 (Basis)
    @Resolvers({@Resolver(provider = "onvista-fundamental",
            extractors = {
                    @Extract(searchType = SearchType.Selector, expression = "article.KENNZAHLEN table th.ZAHL:nth-of-type(2)"),
                    @Extract(searchType = SearchType.Selector, expression = "article.KENNZAHLEN table tbody tr:nth-of-type(2) td:nth-of-type(2)"),
                    @Extract(searchType = SearchType.Selector, expression = "article.KENNZAHLEN table th.ZAHL:nth-of-type(3)"),
                    @Extract(searchType = SearchType.Selector, expression = "article.KENNZAHLEN table tbody tr:nth-of-type(2) td:nth-of-type(3)"),
                    @Extract(searchType = SearchType.Selector, expression = "article.KENNZAHLEN table th.ZAHL:nth-of-type(4)"),
                    @Extract(searchType = SearchType.Selector, expression = "article.KENNZAHLEN table tbody tr:nth-of-type(2) td:nth-of-type(4)"),
                    @Extract(searchType = SearchType.Selector, expression = "article.KENNZAHLEN table th.ZAHL:nth-of-type(5)"),
                    @Extract(searchType = SearchType.Selector, expression = "article.KENNZAHLEN table tbody tr:nth-of-type(2) td:nth-of-type(5)"),
                    @Extract(searchType = SearchType.Selector, expression = "article.KENNZAHLEN table th.ZAHL:nth-of-type(6)"),
                    @Extract(searchType = SearchType.Selector, expression = "article.KENNZAHLEN table tbody tr:nth-of-type(2) td:nth-of-type(6)"),
                    @Extract(searchType = SearchType.Selector, expression = "article.KENNZAHLEN table th.ZAHL:nth-of-type(7)"),
                    @Extract(searchType = SearchType.Selector, expression = "article.KENNZAHLEN table tbody tr:nth-of-type(2) td:nth-of-type(7)"),
                    @Extract(searchType = SearchType.Selector, expression = "article.KENNZAHLEN table th.ZAHL:nth-of-type(8)"),
                    @Extract(searchType = SearchType.Selector, expression = "article.KENNZAHLEN table tbody tr:nth-of-type(2) td:nth-of-type(8)")
            },
            source = Source.RESPONSE,
            converterClass = PerConverter.class,
            validators = {@Validate(expression = "article.KENNZAHLEN table th.ZAHL", expected = "2018e"),
                    @Validate(expression = "article.KENNZAHLEN table tbody tr:nth-of-type(2) td.INFOTEXT", expected = "KGV")})})
    private Map<Year, String> per;

    @Resolvers({@Resolver(provider = "onvista-fundamental",
            extractors = {@Extract(searchType = SearchType.Selector, expression = "article.KENNZAHLEN div span")},
            source = Source.RESPONSE,
            converterClass = FinancialYearConverter.class)})
    private LocalDate financialYear;

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
    private String[] quarterQuoteIndex;

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

    public String getStockname() {
        return stockname;
    }

    public String getIsin() {
        return isin;
    }

    public Map<Dataprovider, String> getUrlParts() {
        return urlParts;
    }

    public Map<Dataprovider, String> getHistoryParts() {
        return historyParts;
    }

    public String getSymbol() {
        return symbol;
    }

    public LocalDate getFetchDate() {
        return fetchDate;
    }

    public BigDecimal getRoe() {
        return roe;
    }

    public BigDecimal getEbitMargin() {
        return ebitMargin;
    }

    public BigDecimal getEquityRatio() {
        return equityRatio;
    }

    public Map<Year, String> getPer() {
        return per;
    }

    public String getAnalystsOpinion() {
        return analystsOpinion;
    }

    public String getAnalystsCount() {
        return analystsCount;
    }

    public String getQuote() {
        return quote;
    }

    public String[] getQuarterQuote() {
        return quarterQuote;
    }

    public String[] getQuarterQuoteIndex() {
        return quarterQuoteIndex;
    }

    public String getEarningsRevision() {
        return earningsRevision;
    }

    public String getStockPriceHalfYear() {
        return stockPriceHalfYear;
    }

    public String getStockPriceYear() {
        return stockPriceYear;
    }

    public String[] getThreeMonthReversal() {
        return threeMonthReversal;
    }

    public String getEpsLY() {
        return epsLY;
    }

    public String getEpsAY() {
        return epsAY;
    }
}

package de.bluemx.stocktool.model;


import de.bluemx.stocktool.annotations.*;
import de.bluemx.stocktool.converter.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.SortedMap;

/**
 * This class is the container for all fetched RawData from different Providers.
 * From theses values others are computed and derived.
 */
@Config(providers = {
        @Provider(name = "onvista-basic", dataprovider = Dataprovider.ONVISTA, url = "http://www.onvista.de/aktien/{urlPart}",
                variables = {@Variable(key = "urlPart", source = "urlParts")},
                required = "urlParts"),
        @Provider(name = "onvista-isin-search", dataprovider = Dataprovider.ONVISTA, url = "http://www.onvista.de/suche/?searchValue={isin}",
                variables = {@Variable(key = "isin", source = "isin")}),
        @Provider(name = "onvista-fundamental", dataprovider = Dataprovider.ONVISTA, url = "http://www.onvista.de/aktien/fundamental/{urlPart}",
                variables = {@Variable(key = "urlPart", source = "urlParts")},
                required = "urlParts"),
        @Provider(name = "onvista-index", dataprovider = Dataprovider.ONVISTA, url = "http://www.onvista.de/index/{indexString}",
                variables = {@Variable(key = "indexString", source = "index")}
        ),
        @Provider(name = "4traders-isin-search", dataprovider = Dataprovider.FOR_TRADERS, url = "http://de.4-traders.com/indexbasegauche.php?mots={isin}",
                variables = {@Variable(key = "isin", source = "isin")}),
        @Provider(name = "4traders-analysts", dataprovider = Dataprovider.FOR_TRADERS, url = "http://de.4-traders.com/{urlPart}/analystenerwartungen/",
                variables = {@Variable(key = "urlPart", source = "urlParts")},
                required = "urlParts"),
        @Provider(name = "onvista-notations",
                dataprovider = Dataprovider.ONVISTA,
                url = "http://www.onvista.de/onvista/times+sales/popup/historische-kurse/?notationId={notationId}&dateStart={lastYearsDate}&interval=Y1&assetName=%20&exchange=Xetra",
                required = "urlParts",
                variables = {
                        @Variable(key = "notationId", source = "historyParts"),
                        @Variable(key = "lastYearsDate", source = "getLastYearsDate")
                })})
public class StockQuoteData {
    @Resolvers({@Resolver(provider = "onvista-basic",
            extractors = {@Extract(searchType = SearchType.Selector, expression = "a.INSTRUMENT")},
            source = Source.RESPONSE_TEXT,
            converter = @Converter(converterClass = SimpleRegExpConverter.class, variables = {"(.*)\\sAktie"})
    )})
    private String stockname;

    private String isin;

    private Index index;

    @Resolvers({
            @Resolver(provider = "onvista-isin-search",
                    source = Source.URL,
                    extractors = {@Extract(searchType = SearchType.REGEXP, expression = "^.*/(.*)")}),
            @Resolver(provider = "4traders-isin-search",
                    source = Source.URL,
                    extractors = {@Extract(searchType = SearchType.REGEXP, expression = "^\\S*/(\\S[^/]+)")})
    })
    @ProviderMap
    private Map<Dataprovider, String> urlParts;

    @Resolvers({@Resolver(provider = "onvista-basic",
            extractors = {@Extract(searchType = SearchType.Selector, expression = "html meta[name=og:image]")},
            source = Source.RESPONSE_TAG,
            converter = @Converter(converterClass = SimpleRegExpConverter.class, variables = {"id=(\\d+)"})
    )})
    @ProviderMap
    private Map<Dataprovider, String> historyParts;


    @Resolvers({@Resolver(provider = "onvista-basic",
            extractors = {@Extract(searchType = SearchType.Selector, expression = "div.WERTPAPIER_DETAILS > dl:nth-of-type(2) > dd")},
            source = Source.RESPONSE_TEXT)})
    private String symbol;
    private LocalDate fetchDate = LocalDate.now();

    // Return of Equity
    // No 1\
    // @TODO Error prone, fetch full table and make decision
    @Resolvers({@Resolver(provider = "onvista-fundamental",
            extractors = {@Extract(searchType = SearchType.Selector, expression = "article.KENNZAHLEN table:nth-of-type(8) tbody tr:nth-of-type(4) td:nth-of-type(6)")},
            source = Source.RESPONSE_TEXT,
            converter = @Converter(converterClass = BigDecimalConverter.class),
            validators = {@Validate(expression = "article.KENNZAHLEN table:nth-of-type(8) tbody tr:nth-of-type(4) td.INFOTEXT", expected = "Eigenkapitalrendite")})})
    private BigDecimal roe;

    // EBIT-Margin
    // No 2
    // @TODO Error prone, fetch full table and make decision
    @Resolvers({@Resolver(provider = "onvista-fundamental",
            extractors = {@Extract(searchType = SearchType.Selector, expression = "article.KENNZAHLEN table:nth-of-type(8) tbody tr:nth-of-type(2) td:nth-of-type(6)")},
            source = Source.RESPONSE_TEXT,
            converter = @Converter(converterClass = BigDecimalConverter.class),
            validators = {@Validate(expression = "article.KENNZAHLEN table:nth-of-type(8) tbody tr:nth-of-type(2) td.INFOTEXT", expected = "EBIT-Marge")})})
    private BigDecimal ebitMargin;

    // Equity Ratio
    // No 3
    @Resolvers({@Resolver(provider = "onvista-fundamental",
            extractors = {@Extract(searchType = SearchType.Selector, expression = "article.KENNZAHLEN table:nth-of-type(6) tbody tr:nth-of-type(2) td:nth-of-type(6)")},
            source = Source.RESPONSE_TEXT,
            converter = @Converter(converterClass = BigDecimalConverter.class),
            validators = {@Validate(expression = "article.KENNZAHLEN table:nth-of-type(6) tbody tr:nth-of-type(2) td.INFOTEXT", expected = "Eigenkapitalquote")})})
    private BigDecimal equityRatio;

    // Price Earnings Ratio / KGV / PER actual No 5
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
            source = Source.RESPONSE_TEXT,
            converter = @Converter(converterClass = PerConverter.class),
            validators = {
                    @Validate(expression = "article.KENNZAHLEN table tbody tr:nth-of-type(2) td.INFOTEXT", expected = "KGV")})})
    private SortedMap<Year, BigDecimal> per;

    @Resolvers({@Resolver(provider = "onvista-fundamental",
            extractors = {@Extract(searchType = SearchType.Selector, expression = "article.KENNZAHLEN div span")},
            source = Source.RESPONSE_TEXT,
            converter = @Converter(converterClass = FinancialYearConverter.class)
    )})
    private LocalDate financialYear;

    // Analysts
    // No 6
    @Resolvers({@Resolver(provider = "4traders-analysts",
            extractors = {@Extract(searchType = SearchType.Selector, expression = "table.Bord td:nth-of-type(2)")},
            source = Source.RESPONSE_TEXT,
            converter = @Converter(converterClass = AnalystsConverter.class)
    )})
    private AnalystsOpinion analystsOpinion;
    @Resolvers({@Resolver(provider = "4traders-analysts",
            extractors = {@Extract(searchType = SearchType.Selector, expression = "table.Bord tr:nth-of-type(2) td:nth-of-type(2)")},
            source = Source.RESPONSE_TEXT,
            converter = @Converter(converterClass = IntegerConverter.class)
    )})
    private int analystsCount;

    @Resolvers({@Resolver(provider = "onvista-notations",
            extractors = {@Extract(searchType = SearchType.Selector, expression = "body table tbody")},
            source = Source.RESPONSE_TEXT,
            converter = @Converter(converterClass = QuoteConverter.class)
    )})
    private SortedMap<LocalDate, BigDecimal> quotes;

    // Reaction Quarter Quote
    // No  7
//    private String[] quarterQuote;
//    private String[] quarterQuoteIndex;


    // Earnigs per Share / Needed for No 8
    // No 13 (Basis)
    @Resolvers({@Resolver(provider = "onvista-fundamental",
            extractors = {@Extract(searchType = SearchType.Selector, expression = "article.KENNZAHLEN div")},
            source = Source.RESPONSE_TEXT,
            converter = @Converter(converterClass = EPSConverter.class),
            validators = {@Validate(expression = "article.KENNZAHLEN table tbody tr:nth-of-type(1) td.INFOTEXT", expected = "Gewinn pro Aktie in EUR")
            }

    )})
    private SortedMap<YearEstimated, BigDecimal> eps;

    public StockQuoteData(String isin, Index index) {
        this.isin = isin;
        this.index = index;
    }

    public String getLastYearsDate() {
        LocalDate minusOneYear = fetchDate.minusYears(1);
        minusOneYear = minusOneYear.minusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.uuuu");
        return minusOneYear.format(formatter);
    }

    public String getStockname() {
        return stockname;
    }

    public void setStockname(String stockname) {
        this.stockname = stockname;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public Index getIndex() {
        return index;
    }

    public void setIndex(Index index) {
        this.index = index;
    }

    public Map<Dataprovider, String> getUrlParts() {
        return urlParts;
    }

    public void setUrlParts(Map<Dataprovider, String> urlParts) {
        this.urlParts = urlParts;
    }

    public Map<Dataprovider, String> getHistoryParts() {
        return historyParts;
    }

    public void setHistoryParts(Map<Dataprovider, String> historyParts) {
        this.historyParts = historyParts;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public LocalDate getFetchDate() {
        return fetchDate;
    }

    public void setFetchDate(LocalDate fetchDate) {
        this.fetchDate = fetchDate;
    }

    public BigDecimal getRoe() {
        return roe;
    }

    public void setRoe(BigDecimal roe) {
        this.roe = roe;
    }

    public BigDecimal getEbitMargin() {
        return ebitMargin;
    }

    public void setEbitMargin(BigDecimal ebitMargin) {
        this.ebitMargin = ebitMargin;
    }

    public BigDecimal getEquityRatio() {
        return equityRatio;
    }

    public void setEquityRatio(BigDecimal equityRatio) {
        this.equityRatio = equityRatio;
    }

    public SortedMap<Year, BigDecimal> getPer() {
        return per;
    }

    public void setPer(SortedMap<Year, BigDecimal> per) {
        this.per = per;
    }

    public LocalDate getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(LocalDate financialYear) {
        this.financialYear = financialYear;
    }

    public AnalystsOpinion getAnalystsOpinion() {
        return analystsOpinion;
    }

    public void setAnalystsOpinion(AnalystsOpinion analystsOpinion) {
        this.analystsOpinion = analystsOpinion;
    }

    public int getAnalystsCount() {
        return analystsCount;
    }

    public void setAnalystsCount(int analystsCount) {
        this.analystsCount = analystsCount;
    }

    public SortedMap<LocalDate, BigDecimal> getQuotes() {
        return quotes;
    }

    public void setQuotes(SortedMap<LocalDate, BigDecimal> quotes) {
        this.quotes = quotes;
    }

    public SortedMap<YearEstimated, BigDecimal> getEps() {
        return eps;
    }

    public void setEps(SortedMap<YearEstimated, BigDecimal> eps) {
        this.eps = eps;
    }
}

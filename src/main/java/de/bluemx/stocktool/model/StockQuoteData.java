package de.bluemx.stocktool.model;


import de.bluemx.stocktool.annotations.*;

import java.time.LocalDate;
import java.util.Map;
@Config({@Provider(name="onvista-basic", url="",
        variables={@Variable(dataprovider = Dataprovider.ONVISTA, attributeName = "urlParts")},
        required="onvista-isin"),
        @Provider(name="onvista-isin-search", url="",
                variables={@Variable(attributeName = "isin")})})
public class StockQuoteData {
    private String stockname;

    private String isin;

    private Map<Dataprovider,String> urlParts;

    @Resolvers({@Resolver(name = "onvista-basic", extractors = {@Extract(searchType = SearchType.REGEXP, expression = "//adasd[]3234")}, source = Source.RESPONSE)})
    private Map<Dataprovider,String> historyParts;

    private String symbol;
    private LocalDate fetchDate;

    // Return of Equity
    // No 1
    private String roe;

    // EBIT-Margin
    // No 2
    private String ebitMargin;

    // Equity Ratio
    // No 3
    private String equityRatio;

    // Price Earnings Ratio
    // No 4 (Basis)
    private Map<Integer, String> per;

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

    public StockQuoteData() {
    }
}

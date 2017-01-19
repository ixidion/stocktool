package de.bluemx.stocktool.mapping;

import de.bluemx.stocktool.db.model.*;
import de.bluemx.stocktool.model.StockQuoteData;
import de.bluemx.stocktool.model.YearEstimated;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public class StockquoteMapperDecorator implements StockquoteMapper {
    private final StockquoteMapper delegate;

    public StockquoteMapperDecorator(StockquoteMapper delegate) {
        this.delegate = delegate;
    }

    @Override
    public StockquoteBasic quoteToBasic(StockQuoteData stockdata, StockquoteBasic basicData) {
        StockquoteBasic basic = delegate.quoteToBasic(stockdata, basicData);
        StockquoteDetail detail = quoteToDetail(stockdata);
        basic.addDetail(detail);
        return basic;
    }

    @Override
    public StockquoteDetail quoteToDetail(StockQuoteData stockdata) {
        StockquoteDetail detail = delegate.quoteToDetail(stockdata);
        detail = mapEps(detail, stockdata);
        detail = mapQuotes(detail, stockdata);
        detail = mapPriceEarningsRatio(detail, stockdata);
        return detail;
    }

    private StockquoteDetail mapPriceEarningsRatio(StockquoteDetail detail, StockQuoteData stockdata) {
        if (stockdata.getPer() == null) return detail;
        for (Map.Entry<YearEstimated, BigDecimal> entry : stockdata.getPer().entrySet()) {
            PriceEarningsRatio per = new PriceEarningsRatio();
            per.setTableYear(entry.getKey().getYear().atDay(1));
            per.setTableValue(entry.getValue());
            detail.addPer(per);
        }
        return detail;
    }

    private StockquoteDetail mapQuotes(StockquoteDetail detail, StockQuoteData stockdata) {
        if (stockdata.getQuotes() == null) return detail;
        for (Map.Entry<LocalDate, BigDecimal> entry : stockdata.getQuotes().entrySet()) {
            HistoricalQuote quote = new HistoricalQuote();
            quote.setQuoteDate(entry.getKey());
            quote.setQuote(entry.getValue());
            detail.addQuote(quote);
        }
        return detail;
    }

    private StockquoteDetail mapEps(StockquoteDetail detail, StockQuoteData stockdata) {
        if (stockdata.getEps() == null) return detail;
        for (Map.Entry<YearEstimated, BigDecimal> entry : stockdata.getEps().entrySet()) {
            Eps eps = new Eps();
            eps.setTableYear(entry.getKey().getLocalDate());
            eps.setEstimated(entry.getKey().isEstimated());
            eps.setTableValue(entry.getValue());
            detail.addEps(eps);
        }
        return detail;
    }
}

package de.bluemx.stocktool.mapping;

import de.bluemx.stocktool.db.model.*;
import de.bluemx.stocktool.model.StockQuoteData;
import de.bluemx.stocktool.model.YearEstimated;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.Vector;

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
        List<PriceEarningsRatio> perList = mapTableData(stockdata.getPer(), detail, PriceEarningsRatio.class);
        List<EbitMargin> ebitMarginList = mapTableData(stockdata.getPer(), detail, EbitMargin.class);
        List<ReturnOnEquity> returnOnEquityList = mapTableData(stockdata.getPer(), detail, ReturnOnEquity.class);
        List<EquityRatio> equityRatioList = mapTableData(stockdata.getPer(), detail, EquityRatio.class);
        List<Eps> epsList = mapTableData(stockdata.getPer(), detail, Eps.class);
        detail.setPriceEarningsRatioList(perList);
        detail.setEbitMarginList(ebitMarginList);
        detail.setEquityRatioList(equityRatioList);
        detail.setReturnOnEquityList(returnOnEquityList);
        detail.setEpsList(epsList);
        detail = mapQuotes(detail, stockdata);
        return detail;
    }

    private <T extends TableKeyValues> List<T> mapTableData(SortedMap<YearEstimated, BigDecimal> entryMap, StockquoteDetail detail, Class<T> clazz) {
        List<T> keyValueList = new Vector<T>();
        if (!(entryMap == null)) {
            for (Map.Entry<YearEstimated, BigDecimal> entry : entryMap.entrySet()) {
                T keyValuesv = null;
                try {
                    keyValuesv = clazz.newInstance();
                    keyValuesv.setTableYear(entry.getKey().getYear().atDay(1));
                    keyValuesv.setEstimated(entry.getKey().isEstimated());
                    keyValuesv.setTableValue(entry.getValue());
                    keyValuesv.setStockquoteDetail(detail);
                    keyValueList.add(keyValuesv);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return keyValueList;
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
}

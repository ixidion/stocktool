package de.bluemx.stocktool;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by Patrick Labonte on 09.12.2016.
 */
public class SimpleYahooFetch {
    public static void main(String[] args) throws IOException {
        Stock stock = YahooFinance.get("VOW3.DE");

        BigDecimal price = stock.getQuote().getPrice();
        BigDecimal change = stock.getQuote().getChangeInPercent();
        BigDecimal peg = stock.getStats().getPeg();
        BigDecimal dividend = stock.getDividend().getAnnualYieldPercent();

        stock.print();

        Document doc = Jsoup.connect("http://en.wikipedia.org/").get();
        Elements newsHeadlines = doc.select("#mp-itn b a");
    }
}

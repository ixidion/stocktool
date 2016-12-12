package de.bluemx.stocktool;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;

public class SimpleYahooFetch {
    public static void main(String[] args) throws IOException {
        Stock stock = YahooFinance.get("VOW3.DE");

        BigDecimal price = stock.getQuote().getPrice();
        BigDecimal change = stock.getQuote().getChangeInPercent();
        BigDecimal peg = stock.getStats().getPeg();
        BigDecimal dividend = stock.getDividend().getAnnualYieldPercent();

        stock.print();

        Connection con = Jsoup.connect("http://www.onvista.de/suche/?searchValue=DE000A1K0409")
                .userAgent("Mozilla");
        con.get();
        System.out.println(con.response().url());
    }
}

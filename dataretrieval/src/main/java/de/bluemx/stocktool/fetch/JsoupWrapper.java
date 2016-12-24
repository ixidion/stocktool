package de.bluemx.stocktool.fetch;

import com.google.inject.Singleton;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Singleton
public class JsoupWrapper {

    public Connection connect(String url) {
        return Jsoup.connect(url);
    }

    public Document parse(String html) {
        return Jsoup.parse(html);
    }

}

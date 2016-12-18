package de.bluemx.stocktool.fetch;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

@Singleton
public class JsoupWrapper {

    public Connection connect(String url) {
        return Jsoup.connect(url);
    }

}

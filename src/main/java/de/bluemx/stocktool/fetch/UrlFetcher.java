package de.bluemx.stocktool.fetch;

import com.google.inject.Inject;
import de.bluemx.stocktool.annotations.*;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static de.bluemx.stocktool.helper.StringUtil.extractPatternFromString;

public class UrlFetcher {
    final static Logger log = LoggerFactory.getLogger(UrlFetcher.class);

    private JsoupWrapper jsoup;

    @Inject
    public UrlFetcher(JsoupWrapper jsoup) {
        this.jsoup = jsoup;
    }

    public String[] urlFetch(String url, Provider provider, Resolver resolver, Object obj) {
        Dataprovider dataprovider = provider.dataprovider();
        if (dataprovider.equals(Dataprovider.YAHOO)) {
            return callYahooAPi();
        } else {
            return callJsoupAPI(url, resolver);
        }

    }

    private String[] callJsoupAPI(String url, Resolver resolver) {
        Source source = resolver.source();
        Connection con = jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/602.2.14 (KHTML, like Gecko) Version/10.0.1 Safari/602.2.14")
                .timeout(10000); // 10sec
        String resultingUrl;
        try {
            if (source.equals(Source.RESPONSE_TEXT)) {
                Document doc = con.get();
                validateResponse(doc, resolver);
                return extractFromResponse(doc, resolver);
            } else if (source.equals(Source.RESPONSE_TAG)) {
                Document doc = con.get();
                validateResponse(doc, resolver);
                return extractTagFromResponse(doc, resolver);
            } else if (source.equals(Source.URL)){
                // After forwarding to new page
                resultingUrl = con.response().url().toString();
                return  extractFromUrl(resultingUrl, resolver);
            } else {
                log.error("The given source {} ist not implemented.", source.toString());
                throw new  IllegalArgumentException("Given Source not implemented.");
            }
        } catch (IOException e) {
            log.error("Fetch impossible.");
            throw new RuntimeException(e);
        }
    }

    private void validateResponse(Document doc, Resolver resolver) {
        List<String> results = new ArrayList<>();
        for (Validate validator : resolver.validators()) {
            String extract = doc.select(validator.expression()).first().text();
            if (!extract.equals(validator.expected())) {
                throw new ValidationException("Validation failed.", validator, extract);
            }
        }

    }

    private String[] extractTagFromResponse(Document doc, Resolver resolver) {
        List<String> results = new ArrayList<>();
        for (Extract extractor : resolver.extractors()) {
            Elements select = doc.select(extractor.expression());
            if (select.size() > 0) {
                results.add(select.first().toString());
            }
        }
        return results.toArray(new String[results.size()]);

    }

    private String[] extractFromResponse(Document doc, Resolver resolver) {
        List<String> results = new ArrayList<>();
        for (Extract extractor : resolver.extractors()) {
            if (extractor.searchType().equals(SearchType.Selector)) {
                Elements select = doc.select(extractor.expression());
                if (select.size() > 0) {
                    results.add(select.first().text());
                }
            }
        }
        return results.toArray(new String[results.size()]);

    }

    private String[] extractFromUrl(String resultingUrl, Resolver resolver) {
        List<String> results = new ArrayList<>();
        if (resolver.extractors().length == 1) {
            Extract extractor = resolver.extractors()[0];
            if (extractor.searchType().equals(SearchType.REGEXP)) {
                String[] strArray = new String[1];
                strArray[0] = extractPatternFromString(resultingUrl, extractor.expression());

                return strArray;
            }
            throw new IllegalArgumentException("you have defined none oder more than one Extractor.");
        }
        throw new IllegalArgumentException("More than one extractor for type Url.");
    }

    private String[] callYahooAPi() {
        return null;

    }
}

package de.bluemx.stocktool.fetch;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

/**
 * Dummy for mocking
 */
public class DummyResponse implements Connection.Response {
    @Override
    public int statusCode() {
        return 0;
    }

    @Override
    public String statusMessage() {
        return null;
    }

    @Override
    public String charset() {
        return null;
    }

    @Override
    public Connection.Response charset(String charset) {
        return null;
    }

    @Override
    public String contentType() {
        return null;
    }

    @Override
    public Document parse() throws IOException {
        return null;
    }

    @Override
    public String body() {
        return null;
    }

    @Override
    public byte[] bodyAsBytes() {
        return new byte[0];
    }

    @Override
    public URL url() {
        return null;
    }

    @Override
    public Connection.Response url(URL url) {
        return null;
    }

    @Override
    public Connection.Method method() {
        return null;
    }

    @Override
    public Connection.Response method(Connection.Method method) {
        return null;
    }

    @Override
    public String header(String name) {
        return null;
    }

    @Override
    public Connection.Response header(String name, String value) {
        return null;
    }

    @Override
    public boolean hasHeader(String name) {
        return false;
    }

    @Override
    public boolean hasHeaderWithValue(String name, String value) {
        return false;
    }

    @Override
    public Connection.Response removeHeader(String name) {
        return null;
    }

    @Override
    public Map<String, String> headers() {
        return null;
    }

    @Override
    public String cookie(String name) {
        return null;
    }

    @Override
    public Connection.Response cookie(String name, String value) {
        return null;
    }

    @Override
    public boolean hasCookie(String name) {
        return false;
    }

    @Override
    public Connection.Response removeCookie(String name) {
        return null;
    }

    @Override
    public Map<String, String> cookies() {
        return null;
    }
}

package org.apache.coyote.http11.request;

import java.util.Map;

public class HttpCookies {

    private final Map<String, String> cookies;

    public HttpCookies(String cookies) {
        this.cookies = HttpRequestParser.parseCookies(cookies);
    }

    public String get(String name) {
        return cookies.get(name);
    }

    public String getCookie(String name) {
        return cookies.get(name);
    }
}

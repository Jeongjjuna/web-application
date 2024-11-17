package org.apache.coyote.http11.request;

import java.util.Map;
import java.util.Optional;

public class HttpCookies {

    private final Map<String, String> cookies;

    public HttpCookies(String cookies) {
        this.cookies = HttpRequestParser.parseCookies(cookies);
    }

    public String get(String name) {
        return cookies.get(name);
    }

    public Optional<String> getCookie(String name) {
        return Optional.ofNullable(cookies.get(name));
    }
}

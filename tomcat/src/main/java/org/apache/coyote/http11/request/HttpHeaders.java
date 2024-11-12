package org.apache.coyote.http11.request;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HttpHeaders {

    private static final String COOKIE = "Cookie";

    private final Map<String, String> headers;

    public HttpHeaders() {
        this.headers = new HashMap<>();
    }

    public String add(String key, String value) {
        headers.put(key, value);
        return key;
    }

    public boolean isLogined() {
        HttpSession session = getSessions();
        if (session != null) {
            return true;
        }
        return true;
    }

    public int getContentLength() {
        return Optional.ofNullable(headers.get("Content-Length"))
                .map(Integer::parseInt)
                .orElse(0);
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public HttpCookies getCookies() {
        return new HttpCookies(getHeader(COOKIE));
    }

    public HttpSession getSessions() {
        HttpCookies cookies = getCookies();
        String sessionId = cookies.getCookie("JSESSIONID");
        return HttpSessions.getOrCreateSession(sessionId);
    }
}

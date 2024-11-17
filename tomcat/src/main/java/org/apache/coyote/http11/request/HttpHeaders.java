package org.apache.coyote.http11.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HttpHeaders {

    public static final String JSESSIONID = "JSESSIONID";

    private static final Logger log = LoggerFactory.getLogger(HttpHeaders.class);

    private static final String COOKIE = "Cookie";
    private static final String CONTENT_LENGTH = "Content-Length";

    private final Map<String, String> headers;

    public HttpHeaders() {
        this.headers = new HashMap<>();
    }

    public String add(String key, String value) {
        headers.put(key, value);
        return key;
    }

    public boolean isLogined() {
        // 1. 요청 헤더값에 JSession 값이 없으면 일단 로그인 False
        HttpCookies cookies = getCookies();
        Optional<String> jSessionId = cookies.getCookie(JSESSIONID);
        if (jSessionId.isEmpty()) {
            log.error("[ERROR] 요청헤더에 로그인 정보가 없습니다.");
            return false;
        }

        // 2. JSession 값이 SessionManage에 저장되어있지 않다면 False
        return SessionManager.isLogin(jSessionId.get());
    }

    public int getContentLength() {
        return Optional.ofNullable(headers.get(CONTENT_LENGTH))
                .map(Integer::parseInt)
                .orElse(0);
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public HttpCookies getCookies() {
        return new HttpCookies(getHeader(COOKIE));
    }

}

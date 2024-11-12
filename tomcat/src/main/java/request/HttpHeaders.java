package request;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HttpHeaders {

    private final Map<String, String> headers;

    public HttpHeaders() {
        this.headers = new HashMap<>();
    }

    public String add(String key, String value) {
        headers.put(key, value);
        return key;
    }

    public boolean isLogined() {
        return Optional.ofNullable(headers.get("Cookie"))
                .map(cookies -> cookies.split(", "))
                .stream()
                .flatMap(Arrays::stream)
                .anyMatch(cookie -> cookie.equals("logined=true"));
    }

    public int getContentLength() {
        return Optional.ofNullable(headers.get("Content-Length"))
                .map(Integer::parseInt)
                .orElse(0);
    }

    public String getHeader(String key) {
        return headers.get(key);
    }
}

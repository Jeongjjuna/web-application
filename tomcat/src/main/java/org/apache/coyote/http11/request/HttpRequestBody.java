package org.apache.coyote.http11.request;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestBody {

    private final Map<String, String> body;

    public HttpRequestBody() {
        this.body = new HashMap<>();
    }

    public String add(String key, String value) {
        body.put(key, value);
        return key;
    }

    public Map<String, String> getBody() {
        return body;
    }

    public String getParameter(String key) {
        return body.get(key);
    }
}

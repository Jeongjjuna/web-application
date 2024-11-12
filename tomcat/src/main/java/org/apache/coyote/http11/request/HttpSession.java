package org.apache.coyote.http11.request;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {

    private final Map<String, Object> values = new HashMap<>();
    private final String id;

    public HttpSession(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setAttribute(String key, Object value) {
        values.put(key, value);
    }

    public Object getAttribute(String key) {
        return values.get(key);
    }

    public void removeAttribute(String key) {
        values.remove(key);
    }

    public void invalidate() {
        HttpSessions.remove(id);
    }
}

package request;

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

    public int getContentLength() {
        return Optional.ofNullable(headers.get("Content-Length"))
                .map(Integer::parseInt)
                .orElse(0);
    }

    public boolean isLogined() {
        if (headers.containsKey("Cookie")) {
            String cookies = headers.get("Cookie");
            String[] cookieArray = cookies.split(", ");
            for (String cookie : cookieArray) {
                if (cookie.equals("logined=true")) {
                    return true;
                }
            }
        }
        
        return false;
    }
}

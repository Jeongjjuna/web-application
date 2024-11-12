package request;

import exception.BaseException;

import java.util.Arrays;

public enum HttpMethod {
    GET, POST, PUT, DELETE;

    public static HttpMethod create(String httpMethodName) {
        return Arrays.stream(HttpMethod.values())
                .filter(httpMethod -> httpMethod.name().equals(httpMethodName))
                .findFirst()
                .orElseThrow(() -> new BaseException("[ERROR] Invalid HttpMethod: " + httpMethodName));
    }

    public boolean isPost() {
        return this == POST;
    }

    public boolean isGet() {
        return this == GET;
    }
}

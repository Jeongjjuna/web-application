package org.apache.coyote.http11.request;

public class HttpRequestLine {

    private final HttpMethod method;
    private final String url;
    private final String version;

    public HttpRequestLine(String[] lineTokens) {
        this.method = HttpMethod.create(lineTokens[0]);
        this.url = lineTokens[1];
        this.version = lineTokens[2];
    }

    public boolean isGetRequest() {
        return method == HttpMethod.GET;
    }

    public boolean isPostRequest() {
        return method == HttpMethod.POST;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return HttpRequestParser.getPath(url);
    }
}

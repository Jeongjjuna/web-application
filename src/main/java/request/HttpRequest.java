package request;


public class HttpRequest {

    private final HttpRequestLine httpRequestLine;
    private final HttpHeaders httpHeaders;

    public HttpRequest(HttpRequestLine httpRequestLine, HttpHeaders httpHeaders) {
        this.httpRequestLine = httpRequestLine;
        this.httpHeaders = httpHeaders;
    }

    public String getUrl() {
        return httpRequestLine.getUrl();
    }
}

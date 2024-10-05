package request;


public class HttpRequest {

    private final HttpRequestLine httpRequestLine;
    private final HttpHeaders httpHeaders;
    private final HttpRequestBody httpRequestBody;

    public HttpRequest(HttpRequestLine httpRequestLine, HttpHeaders httpHeaders, HttpRequestBody httpRequestBody) {
        this.httpRequestLine = httpRequestLine;
        this.httpHeaders = httpHeaders;
        this.httpRequestBody = httpRequestBody;
    }

    public String getUrl() {
        return httpRequestLine.getUrl();
    }

    public HttpMethod getHttpMethod() {
        return httpRequestLine.getMethod();
    }

    public HttpRequestBody getHttpRequestBody() {
        return httpRequestBody;
    }
}

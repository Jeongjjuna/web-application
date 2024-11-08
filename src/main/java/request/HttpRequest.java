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

    public boolean isGetRequest() {
        return httpRequestLine.isGetRequest();
    }

    public boolean isPostRequest() {
        return httpRequestLine.isPostRequest();
    }

    public boolean isLogined() {
        return httpHeaders.isLogined();
    }

    public String getPath() {
        return httpRequestLine.getPath();
    }

    public HttpMethod getHttpMethod() {
        return httpRequestLine.getMethod();
    }

    public HttpRequestBody getHttpRequestBody() {
        return httpRequestBody;
    }

    public String getHeader(String key) {
        return httpHeaders.getHeader(key);
    }

    public String getParameter(String key) {
        return httpRequestBody.getParameter(key);
    }
}

package request;

import java.io.IOException;

public class HttpRequestLine {

    private final HttpMethod method;
    private final String url;
    private final String version;

    public HttpRequestLine(String[] lineTokens) throws IOException {
        this.method = HttpMethod.create(lineTokens[0]);
        this.url = lineTokens[1];
        this.version = lineTokens[2];
    }

    public String getUrl() {
        return url;
    }
}

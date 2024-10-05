import request.HttpHeaders;
import request.HttpRequest;
import request.HttpRequestLine;
import utils.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequestParser {

    public static HttpRequest getHttpRequest(BufferedReader br) throws IOException {
        HttpRequestLine httpRequestLine = getRequestLine(br);
        HttpHeaders httpHeaders = getHttpRequestHeaders(br);

        return new HttpRequest(httpRequestLine, httpHeaders);
    }

    private static HttpRequestLine getRequestLine(BufferedReader br) throws IOException {
        String requestLine = br.readLine();
        String[] lineTokens = StringUtils.split(requestLine, " ");
        return new HttpRequestLine(lineTokens);
    }

    private static HttpHeaders getHttpRequestHeaders(BufferedReader br) throws IOException {
        HttpHeaders httpHeaders = new HttpHeaders();
        String line;
        while ((line = br.readLine()) != null) {
            String[] tokens = StringUtils.split(line, ":");
            String key = tokens[0];
            String value = StringUtils.trim(tokens[1]);
            httpHeaders.add(key, value);
        }
        return httpHeaders;
    }

}

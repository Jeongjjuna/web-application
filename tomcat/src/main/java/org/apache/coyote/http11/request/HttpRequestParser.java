package org.apache.coyote.http11.request;

import com.yjh.utils.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class HttpRequestParser {

    public static HttpRequest getHttpRequest(InputStream in) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        HttpRequestLine httpRequestLine = getRequestLine(br);
        HttpHeaders httpHeaders = getHttpRequestHeaders(br);
        HttpRequestBody httpRequestBody = getHttpRequestBody(br, httpHeaders.getContentLength());
        return new HttpRequest(httpRequestLine, httpHeaders, httpRequestBody);
    }

    public static String getPath(String url) {
        return url.split("\\?")[0];
    }

    private static HttpRequestLine getRequestLine(BufferedReader br) throws IOException {
        String requestLine = br.readLine();
        String[] lineTokens = StringUtils.split(requestLine, " ");
        return new HttpRequestLine(lineTokens);
    }

    private static HttpHeaders getHttpRequestHeaders(BufferedReader br) throws IOException {
        HttpHeaders httpHeaders = new HttpHeaders();

        String line;
        while (!(line = br.readLine()).isEmpty()) {
            String[] tokens = StringUtils.split(line, ":");
            String key = tokens[0];
            String value = StringUtils.trim(tokens[1]);
            httpHeaders.add(key, value);
        }

        return httpHeaders;
    }

    private static HttpRequestBody getHttpRequestBody(BufferedReader br, int contentLength) throws IOException {

        HttpRequestBody httpRequestBody = new HttpRequestBody();

        char[] bodyChars = new char[contentLength];
        br.read(bodyChars, 0, contentLength);
        String body;
        body = new String(bodyChars).trim();
        body = URLDecoder.decode(body, StandardCharsets.UTF_8);

        if (body.isEmpty()) {
            return httpRequestBody;
        }

        String[] tokens = StringUtils.split(body, "&");
        for (String token : tokens) {
            String[] keyValue = StringUtils.split(token, "=");
            httpRequestBody.add(keyValue[0], keyValue[1]);
        }

        return httpRequestBody;
    }

}

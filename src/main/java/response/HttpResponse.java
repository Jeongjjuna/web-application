package response;

import exception.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class HttpResponse {

    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);

    private final Map<String, String> headers = new HashMap<>();
    private final DataOutputStream dos;

    public HttpResponse(OutputStream outputStream) {
        this.dos = new DataOutputStream(outputStream);
    }

    public static HttpResponse of(OutputStream out) {
        return new HttpResponse(out);
    }

    /**
     * html, css, js 파일을 직접 읽어서 응답으로 보내는 메서드이다.
     * @param path : host 이후부터 쿼리스트링 이전까지를 나타낸다. ex) /user/login.html
     */
    public void forward(String path) {
        try {
            // 요청 resource 받아오기.
            URL resourceUrl = getResourceUrlFrom(path);
            byte[] body = getBodyDataFrom(resourceUrl);

            // 헤더값 추가하기
            if (path.endsWith(".html")) {
                addheader("Content-Type", "text/html;charset=utf-8");
            } else if (path.endsWith(".css")) {
                addheader("Content-Type", "text/css");
            } else if (path.endsWith(".js")) {
                addheader("Content-Type", "application/javascript");
            }
            addheader("Content-Length", String.valueOf(body.length));

            // 응답하기
            response200Header();
            responseBody(body);

        } catch (BaseException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void forwardBody(String data) throws IOException {
        byte[] body = data.getBytes(StandardCharsets.UTF_8);
        addheader("Content-Type", "text/html;charset=utf-8");
        addheader("Content-Length", String.valueOf(body.length));
        response200Header();
        responseBody(body);
    }

    public void sendRedirect(String redirectUrl) throws IOException {
        dos.writeBytes("HTTP/1.1 302 Found \r\n");
        processHeaders();
        dos.writeBytes("Location: " + redirectUrl + " \r\n");
        dos.writeBytes("\r\n");
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    private void response200Header() throws IOException {
        dos.writeBytes("HTTP/1.1 200 OK \r\n");
        processHeaders();
    }

    private void processHeaders() throws IOException {
        Set<String> keys = headers.keySet();
        for (String key : keys) {
            dos.writeBytes(key + ": " + headers.get(key) + " \r\n");
        }
    }

    private void responseBody(byte[] body) throws IOException {
        dos.writeBytes("\r\n");
        dos.write(body, 0, body.length);
        dos.flush();
    }

    private void addheader(String key, String value) {
        headers.put(key, value);
    }

    private byte[] getBodyDataFrom(URL resourceUrl) throws IOException, URISyntaxException {
        Path resourcePath = Paths.get(resourceUrl.toURI());
        return Files.readAllBytes(resourcePath);
    }

    private URL getResourceUrlFrom(String path) {
        URL resourceUrl = getClass()
                .getClassLoader()
                .getResource("./webapp" + path);

        return Objects.requireNonNull(resourceUrl, "[ERROR] Not Found Resource : " + path);
    }
}

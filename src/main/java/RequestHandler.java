import exception.BaseException;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpMethod;
import request.HttpRequest;
import request.HttpRequestBody;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;

public class RequestHandler implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket clientSocket;

    public RequestHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        log.info("[{}] : > 요청", Thread.currentThread().getName());

        try (InputStream in = clientSocket.getInputStream(); OutputStream out = clientSocket.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            DataOutputStream dos = new DataOutputStream(out);

            HttpRequest httpRequest = HttpRequestParser.getHttpRequest(br);
            HttpMethod httpMethod = httpRequest.getHttpMethod();
            String requestUrl = httpRequest.getUrl();

            if (httpMethod == HttpMethod.GET) {
                URL url = getClass()
                        .getClassLoader()
                        .getResource("./webapp" + requestUrl);
                Objects.requireNonNull(url);
                Path path = Paths.get(url.toURI());
                byte[] body = Files.readAllBytes(path);

                response200Header(dos, body.length);
                responseBody(dos, body);
            }

            if (httpMethod == HttpMethod.POST) {
                if (requestUrl.equals("/user/create")) {
                    HttpRequestBody httpRequestBody = httpRequest.getHttpRequestBody();
                    Map<String, String> bodyData = httpRequestBody.getBody();
                    User user = new User(
                            bodyData.get("username"),
                            bodyData.get("email"),
                            bodyData.get("password")
                    );

                    // TODO("저장기능 구현")
                    response302Header(dos, 0);
                    responseBody(dos, new byte[0]);
                }
            }

        } catch (BaseException e) {
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            log.info("[{}] : < 응답", Thread.currentThread().getName());
        }
    }

    private void response200Header(DataOutputStream dos, int bodyLength) throws IOException {
        dos.writeBytes("HTTP/1.1 200 OK \r\n");
        dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
        dos.writeBytes("Content-Length: " + bodyLength + "\r\n");
        dos.writeBytes("\r\n");
    }

    private void response302Header(DataOutputStream dos, int bodyLength) throws IOException {
        dos.writeBytes("HTTP/1.1 302 Found \r\n");
        dos.writeBytes("Location: /index.html\r\n");
        dos.writeBytes("Content-Length: " + bodyLength + "\r\n");
        dos.writeBytes("\r\n");
    }

    private void responseBody(DataOutputStream dos, byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }

}

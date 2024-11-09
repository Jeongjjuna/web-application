package controller;

import exception.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import response.HttpResponse;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

public class RequestHandler implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private final Map<String, Controller> controllers = Map.of(
            "/user/create", new CreateUserController(),
            "/user/list", new ListUserController(),
            "/user/login", new LoginController()
    );

    private final Socket clientSocket;

    public RequestHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        log.info("[{}] : > 요청", Thread.currentThread().getName());

        try (InputStream in = clientSocket.getInputStream(); OutputStream out = clientSocket.getOutputStream()) {

            HttpRequest httpRequest = HttpRequest.of(in);
            HttpResponse httpResponse = HttpResponse.of(out);

            String path = httpRequest.getPath();

            Controller controller = controllers.get(path);

            /**
             * 해당하는 컨트롤러가 없다면, httpResponse forward 메서드를 호출
             */
            if (controller == null) {
                httpResponse.forward(path);
                return;
            }

            controller.service(httpRequest, httpResponse);

        } catch (BaseException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            log.info("[{}] : < 응답", Thread.currentThread().getName());
        }
    }
}

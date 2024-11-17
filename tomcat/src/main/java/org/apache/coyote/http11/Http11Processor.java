package org.apache.coyote.http11;

import com.yjh.controller.Controller;
import com.yjh.exception.BaseException;
import org.apache.coyote.Processor;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);

    private final Socket connection;

    public Http11Processor(final Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        process(connection);
    }

    @Override
    public void process(final Socket connection) {
        log.info("[{}] : > 요청", Thread.currentThread().getName());
        try (InputStream in = connection.getInputStream();
             OutputStream out = connection.getOutputStream()) {

            HttpRequest httpRequest = HttpRequest.of(in);
            HttpResponse httpResponse = HttpResponse.of(out);

            String path = httpRequest.getPath();
            Controller controller = RequestMapping.getController(path);

            /**
             * 해당하는 컨트롤러가 없다면, httpResponse forward 메서드를 호출
             */
            if (controller == null) {
                httpResponse.forward(path);
                return;
            }

            controller.service(httpRequest, httpResponse);
        } catch (IOException | BaseException e) {
            log.error(e.getMessage(), e);
        } finally {
            log.info("[{}] : < 응답", Thread.currentThread().getName());
        }
    }
}


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebApplicationServer {

    private static final Logger log = LoggerFactory.getLogger(WebApplicationServer.class);
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private static final int DEFAULT_PORT = 8080;

    private final int port;

    public WebApplicationServer(int port) {
        this.port = port;
    }

    public WebApplicationServer() {
        this.port = DEFAULT_PORT;
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            log.info("[INFO] 서버가 시작되었습니다 : {}", port);

            Socket clientSocket;
            while ((clientSocket = serverSocket.accept()) != null) {
                RequestHandler requestHandler = new RequestHandler(clientSocket);
                executorService.execute(requestHandler);
            }

        } catch (IOException e) {
            log.error("[ERROR] {}", e.getMessage());
        }
    }
}

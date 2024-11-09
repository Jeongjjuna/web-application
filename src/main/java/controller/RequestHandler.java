package controller;

import db.DataBase;
import exception.BaseException;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import response.HttpResponse;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

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

            HttpRequest httpRequest = HttpRequest.of(in);
            HttpResponse httpResponse = HttpResponse.of(out);

            String path = httpRequest.getPath();

            if (path.equals("/user/list")) {
                if (!httpRequest.isLogined()) {
                    httpResponse.sendRedirect("/index.html");
                    return;
                }
                StringBuilder html = new StringBuilder();
                html.append("<!DOCTYPE html>\n")
                        .append("<html lang=\"ko\">\n")
                        .append("<head>\n")
                        .append("    <meta charset=\"UTF-8\">\n")
                        .append("    <title>Web Application Study</title>\n")
                        .append("</head>\n")
                        .append("<body>\n")
                        .append("    <div>사용자 리스트</div>\n")
                        .append("\n")
                        .append("    <ul>\n")
                        .append("        <li><a href=\"/index.html\" role=\"button\">홈 페이지</a></li>\n")
                        .append("        <li><a href=\"/user/login.html\" role=\"button\">로그인 페이지</a></li>\n")
                        .append("        <li><a href=\"/user/form.html\" role=\"button\">회원가입 페이지</a></li>\n")
                        .append("    </ul>\n")
                        .append("    <ul>\n");

                List<User> users = DataBase.findAll();
                for (User user : users) {
                    html.append("        <li>" + user.getEmail() + "</li>\n");
                }

                html.append("    </ul>\n")
                        .append("</body>\n")
                        .append("</html>");

                httpResponse.forwardBody(html.toString());

            } else if (path.equals("/user/create")) {
                User user = new User(
                        httpRequest.getParameter("username"),
                        httpRequest.getParameter("email"),
                        httpRequest.getParameter("password")
                );
                DataBase.addUser(user);

                httpResponse.sendRedirect("/index.html");
            } else if (path.equals("/user/login")) {
                try {
                    String username = httpRequest.getParameter("username");

                    User user = DataBase.findUserByUsername(username)
                            .orElseThrow(() -> new BaseException("[ERROR] Not Found User"));

                    String password = httpRequest.getParameter("password");
                    if (!user.isSamePassword(password)) {
                        throw new BaseException("[ERROR] Wrong Password");
                    }

                    httpResponse.addHeader("Set-Cookie", "logined=true");
                    httpResponse.sendRedirect("/user/login.html");
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    httpResponse.sendRedirect("/user/login_failed.html");
                }
            } else {
                httpResponse.forward(path);
            }
        } catch (
                BaseException e) {
            log.error(e.getMessage());
        } catch (
                Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            log.info("[{}] : < 응답", Thread.currentThread().getName());
        }
    }
}

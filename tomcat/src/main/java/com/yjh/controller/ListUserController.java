package com.yjh.controller;

import com.yjh.db.DataBase;
import com.yjh.model.User;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;

import java.util.List;

public class ListUserController extends AbstractController {

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        if (!request.isLogined()) {
            response.sendRedirect("/index.html");
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

        response.forwardBody(html.toString());
    }
}

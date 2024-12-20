package com.yjh.controller;

import com.yjh.db.DataBase;
import com.yjh.model.User;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;

public class CreateUserController extends AbstractController {

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        User user = new User(
                request.getParameter("username"),
                request.getParameter("email"),
                request.getParameter("password")
        );
        DataBase.addUser(user);

        response.sendRedirect("/index.html");
    }
}

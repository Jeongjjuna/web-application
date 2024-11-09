package controller;

import db.DataBase;
import model.User;
import request.HttpRequest;
import response.HttpResponse;

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

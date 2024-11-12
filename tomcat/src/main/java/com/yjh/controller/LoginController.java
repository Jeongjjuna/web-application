package com.yjh.controller;

import com.yjh.db.DataBase;
import com.yjh.exception.BaseException;
import com.yjh.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;

public class LoginController extends AbstractController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        try {
            String username = request.getParameter("username");

            User user = DataBase.findUserByUsername(username)
                    .orElseThrow(() -> new BaseException("[ERROR] Not Found User"));

            String password = request.getParameter("password");
            if (!user.isSamePassword(password)) {
                throw new BaseException("[ERROR] Wrong Password");
            }

            response.addHeader("Set-Cookie", "logined=true");
            response.sendRedirect("/user/login.html");
        } catch (BaseException e) {
            log.error(e.getMessage());
            response.sendRedirect("/user/login_failed.html");
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            response.sendRedirect("/user/login_failed.html");
        }
    }
}

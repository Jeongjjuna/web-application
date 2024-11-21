package mvc.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mvc.view.ThymeleafView;
import mvc.view.View;

public class NotFoundController implements Controller {

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ThymeleafView("default");
    }
}

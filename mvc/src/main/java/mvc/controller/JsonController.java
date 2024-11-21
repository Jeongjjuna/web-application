package mvc.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mvc.model.User;
import mvc.view.JsonView;
import mvc.view.View;

public class JsonController implements Controller {

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User userModel = new User("name", 10);
        request.setAttribute("user", userModel);
        return new JsonView();
    }
}

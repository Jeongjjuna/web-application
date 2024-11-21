package mvc.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mvc.model.User;
import mvc.view.ModelAndView;

public class JsonController extends AbstractController {

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User userModel = new User("name", 10);
        return jsonView().addObject("user", userModel);
    }
}

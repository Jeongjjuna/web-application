package mvc.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mvc.view.ModelAndView;

import java.util.Objects;

public class ForwardController extends AbstractController {

    private final String forwardUrl;

    public ForwardController(String forwardUrl) {
        this.forwardUrl = forwardUrl;
        Objects.requireNonNull(forwardUrl);
    }

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return thymeleafView(forwardUrl);
    }
}

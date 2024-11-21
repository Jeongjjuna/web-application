package mvc.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mvc.view.ThymeleafView;
import mvc.view.View;

import java.util.Objects;

public class ForwardController implements Controller {

    private final String forwardUrl;

    public ForwardController(String forwardUrl) {
        this.forwardUrl = forwardUrl;
        Objects.requireNonNull(forwardUrl);
    }

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ThymeleafView(forwardUrl);
    }
}

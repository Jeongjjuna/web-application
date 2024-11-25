package mvc.controller;

import mvc.view.JsonView;
import mvc.view.ModelAndView;
import mvc.view.ThymeleafView;

public abstract class AbstractController implements Controller {

    protected ModelAndView thymeleafView(String forwardUrl) {
        return new ModelAndView(new ThymeleafView(forwardUrl));
    }

    protected ModelAndView jsonView() {
        return new ModelAndView(new JsonView());
    }
}
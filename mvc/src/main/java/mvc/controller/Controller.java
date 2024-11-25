package mvc.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mvc.view.ModelAndView;

public interface Controller {

    ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
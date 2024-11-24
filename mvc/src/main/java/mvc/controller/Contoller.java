package mvc.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mvc.view.ModelAndView;

public interface Contoller {

    ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}

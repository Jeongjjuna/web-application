package mvc.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mvc.view.View;

public interface Controller {

    View execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}

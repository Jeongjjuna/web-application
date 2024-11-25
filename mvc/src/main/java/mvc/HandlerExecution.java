package mvc;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mvc.view.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class HandlerExecution {
    private static final Logger log = LoggerFactory.getLogger(HandlerExecution.class);

    private Object decalredObject;
    private Method method;

    public HandlerExecution(Object decalredObject, Method method) {
        this.decalredObject = decalredObject;
        this.method = method;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            return (ModelAndView) method.invoke(decalredObject, request, response);
        } catch (Exception e) {
            log.error("{} method invoke fail. error message : {}", method, e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
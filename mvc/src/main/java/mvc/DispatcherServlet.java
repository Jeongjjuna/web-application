package mvc;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mvc.controller.Controller;
import mvc.mapping.RequestMapping;
import mvc.thymeleaf.ThymeleafConfig;
import mvc.view.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    private RequestMapping requestMapping;
    @Override
    public void init() throws ServletException {
        log.info("DispatcherServlet -> 초기화");
        this.requestMapping = new RequestMapping();
        ThymeleafConfig.initialize();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        log.info("DispatcherServlet -> service() 호출");

        String requestUri = request.getRequestURI();
        Controller controller = requestMapping.getController(requestUri);

        try {
            View view = controller.execute(request, response);
            view.render(request, response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


}

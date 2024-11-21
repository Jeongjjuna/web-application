package mvc;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mvc.controller.Controller;
import mvc.mapping.RequestMapping;
import mvc.thymeleaf.ThymeleafConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final String REDIRECT_PREFIX = "redirect:";

    private RequestMapping requestMapping;
    private TemplateEngine templateEngine;

    @Override
    public void init() throws ServletException {
        log.info("DispatcherServlet -> 초기화");
        this.requestMapping = new RequestMapping();
        this.templateEngine = ThymeleafConfig.getTemplateEngine();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        log.info("DispatcherServlet -> service() 호출");

        String requestUri = request.getRequestURI();
        Controller controller = requestMapping.getController(requestUri);

        try {
            String viewName = controller.execute(request, response);
            move(viewName, response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void move(String viewName, HttpServletResponse response) throws IOException {
        if (viewName.startsWith(REDIRECT_PREFIX)) {
            log.info("DispatcherServlet -> redirect");

            // 방법 1. 리다이렉트 방식 : 프론트에게 302 응답을 해주고, 새로운 Location 으로 요청을 유도한다.
            response.sendRedirect(viewName.substring(REDIRECT_PREFIX.length()));

            // 방법 2. 서버 내에서 직접 다른 서블릿을 호출하거나 리다이렉트 템플릿 뷰를 반환한다.
            return;
        }

        forward(viewName, response);
    }

    private void forward(String viewName, HttpServletResponse response) throws IOException {
        Context context = new Context();
        context.setVariable("message", "Welcome to Thymeleaf!"); // 예시 메시지

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();

        templateEngine.process(viewName, context, writer);
    }
}

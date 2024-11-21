package mvc.view;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mvc.thymeleaf.ThymeleafConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

public class ThymeleafView implements View {

    private static final Logger log = LoggerFactory.getLogger(ThymeleafView.class);
    private static final String REDIRECT_PREFIX = "redirect:";

    private final String viewName;

    public ThymeleafView(String viewName) {
        Objects.requireNonNull(viewName, "[ERROR] view name is null");
        this.viewName = viewName;
    }

    @Override
    public void render(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (viewName.startsWith(REDIRECT_PREFIX)) {
            log.info("DispatcherServlet -> redirect");
            response.sendRedirect(viewName.substring(REDIRECT_PREFIX.length()));
            return;
        }

        forward(viewName, response);
    }

    private void forward(String viewName, HttpServletResponse response) throws IOException {
        Context context = new Context();
        context.setVariable("message", "Welcome to Thymeleaf!"); // 예시 메시지

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();

        TemplateEngine templateEngine = ThymeleafConfig.getTemplateInstance();
        templateEngine.process(viewName, context, writer);
    }
}

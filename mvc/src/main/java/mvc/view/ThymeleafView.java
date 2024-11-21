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
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ThymeleafView implements View {

    private static final Logger log = LoggerFactory.getLogger(ThymeleafView.class);
    private static final String REDIRECT_PREFIX = "redirect:";

    private final String viewName;

    public ThymeleafView(String viewName) {
        Objects.requireNonNull(viewName, "[ERROR] view name is null");
        this.viewName = viewName;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (viewName.startsWith(REDIRECT_PREFIX)) {
            log.info("DispatcherServlet -> redirect");
            response.sendRedirect(viewName.substring(REDIRECT_PREFIX.length()));
            return;
        }

        Context context = createThymeleafContext(model);

        forward(viewName, context, response);
    }

    private void forward(String viewName, Context context, HttpServletResponse response) throws IOException {
        TemplateEngine templateEngine = ThymeleafConfig.getTemplateInstance();
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        templateEngine.process(viewName, context, writer);
    }

    private Context createThymeleafContext(Map<String, ?> model) {
        Context context = new Context();
        Set<String> keys = model.keySet();
        for (String key : keys) {
            context.setVariable(key, model.get(key)); // 예시 메시지
        }
        return context;
    }
}

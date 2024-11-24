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
    public static final String CONTENT_TYPE_HTML = "text/html;charset=UTF-8";

    private final String viewName;

    public ThymeleafView(String viewName) {
        Objects.requireNonNull(viewName, "[ERROR] view name is null");
        this.viewName = viewName;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (viewName.startsWith(REDIRECT_PREFIX)) {
            log.info("-> redirect");
            response.sendRedirect(viewName.substring(REDIRECT_PREFIX.length()));
            return;
        }

        forward(viewName, model, response);
    }

    private void forward(String viewName, Map<String, ?> model, HttpServletResponse response) throws IOException {
        TemplateEngine templateEngine = ThymeleafConfig.getTemplateInstance();
        response.setContentType(CONTENT_TYPE_HTML);
        PrintWriter writer = response.getWriter();

        Context context = createThymeleafContext(model);
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

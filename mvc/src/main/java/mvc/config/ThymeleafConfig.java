package mvc.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.FileTemplateResolver;

public class ThymeleafConfig {

    private static final Logger log = LoggerFactory.getLogger(ThymeleafConfig.class);

    private static TemplateEngine templateEngine = null;

    private ThymeleafConfig() {
    }

    public static void initialize() {
        log.info("ThymeleafTemplate init");
        templateEngine = getTemplateEngine();
    }

    public static TemplateEngine getTemplateInstance() {
        if (templateEngine == null) {
            throw new IllegalStateException("[ERROR] TemplateEngine이 초기화되지 않았습니다. initialize() 메서드를 호출하세요.");
        }
        return templateEngine;
    }

    private static TemplateEngine getTemplateEngine() {
        FileTemplateResolver templateResolver = getFileTemplateResolver();
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }

    private static FileTemplateResolver getFileTemplateResolver() {
        FileTemplateResolver templateResolver = new FileTemplateResolver();
        templateResolver.setPrefix("mvc/webapps/html/"); // 템플릿 파일 경로
        templateResolver.setSuffix(".html"); // 템플릿 파일 확장자
        templateResolver.setTemplateMode("HTML"); // 템플릿 모드 설정
        templateResolver.setCharacterEncoding("UTF-8"); // 인코딩 설정
        return templateResolver;
    }
}


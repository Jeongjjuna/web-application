package mvc.thymeleaf;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class ThymeleafConfig {

    private ThymeleafConfig() {
    }

    public static TemplateEngine getTemplateEngine() {
        ClassLoaderTemplateResolver templateResolver = getClassLoaderTemplateResolver();
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }

    private static ClassLoaderTemplateResolver getClassLoaderTemplateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/"); // 템플릿 파일 경로
        templateResolver.setSuffix(".html"); // 템플릿 파일 확장자
        templateResolver.setTemplateMode("HTML"); // 템플릿 모드 설정
        templateResolver.setCharacterEncoding("UTF-8"); // 인코딩 설정
        return templateResolver;
    }
}

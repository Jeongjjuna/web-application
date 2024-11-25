package mvc;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import mvc.config.ThymeleafConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class ContextLoaderListener implements ServletContextListener {

    private static final Logger log = LoggerFactory.getLogger(ContextLoaderListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("Tomcat init");

        ThymeleafConfig.initialize();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("Tomcat stopped successfully");
    }
}

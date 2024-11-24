import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class WebApplicationServer {

    private static final Logger log = LoggerFactory.getLogger(WebApplicationServer.class);
    private static final String ROOT_PATH = "/";
    private static final String WEB_APPLICATION_PATH = "mvc/webapps/";

    public static void main(String[] args) throws Exception {
        final Tomcat tomcat = new Tomcat();
        setupTomcat(tomcat);

        tomcat.start();
        tomcat.getServer().await();
    }

    private static void setupTomcat(final Tomcat tomcat) {
        setupConnector(tomcat);
        setupContextPath(tomcat);
    }

    private static void setupConnector(final Tomcat tomcat) {
        Connector connector = new Connector();
        connector.setPort(8080);
        tomcat.setConnector(connector);
    }

    private static void setupContextPath(final Tomcat tomcat) {
        tomcat.addWebapp(ROOT_PATH, new File(WEB_APPLICATION_PATH).getAbsolutePath());
        log.info("configuring app with baseDir: {}", new File(WEB_APPLICATION_PATH).getAbsolutePath());
    }
}

package mvc.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter("/*")
public class ResourceFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(ResourceFilter.class);

    private static final List<String> resourcePrefixs = new ArrayList<>();

    static {
        resourcePrefixs.add("/css");
        resourcePrefixs.add("/js");
        resourcePrefixs.add("/fonts");
        resourcePrefixs.add("/images");
        resourcePrefixs.add("/favicon.ico");
    }

    private RequestDispatcher defaultRequestDispatcher;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("ResourceFilter init");
        this.defaultRequestDispatcher = filterConfig.getServletContext().getNamedDispatcher("default");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String path = req.getRequestURI().substring(req.getContextPath().length());
        if (isResourceUrl(path)) {
            log.debug("정적 리소스 요청 : {}", path);
            defaultRequestDispatcher.forward(servletRequest, servletResponse);
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        log.info("ResourceFilter destroy");
    }

    private boolean isResourceUrl(String url) {
        return resourcePrefixs.stream()
                .anyMatch(url::startsWith);
    }

}


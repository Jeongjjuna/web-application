package mvc;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mvc.handler.AnnotationHandlerAdapter;
import mvc.handler.GeneralHandlerAdapter;
import mvc.handler.HandlerAdapter;
import mvc.mapping.AnnotationHandlerMapping;
import mvc.mapping.GeneralHandlerMapping;
import mvc.mapping.HandlerKey;
import mvc.mapping.HandlerMapping;
import mvc.mapping.RequestMethod;
import mvc.view.ModelAndView;
import mvc.view.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    private List<HandlerMapping> handlerMappings;
    private List<HandlerAdapter> handlerAdapters;

    @Override
    public void init() throws ServletException {
        log.info("DispatcherServlet init");

        GeneralHandlerMapping rhm = new GeneralHandlerMapping();
        rhm.initMappings();

        AnnotationHandlerMapping ahm = new AnnotationHandlerMapping("mvc");
        ahm.initMappings();

        this.handlerMappings = List.of(rhm, ahm);
        this.handlerAdapters = List.of(new GeneralHandlerAdapter(), new AnnotationHandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        log.info("DispatcherServlet -> service() 호출");

        String requestUri = request.getRequestURI();
        RequestMethod requestMethod = RequestMethod.valueOf(request.getMethod());

        /**
         * Controller, Object 든 컨트롤러의 역할을 하는 handler 를 찾는다.
         */
        Object handler = handlerMappings.stream()
                .filter(hm -> hm.findHandler(new HandlerKey(requestUri, requestMethod)) != null)
                .map(hm -> hm.findHandler(new HandlerKey(requestUri, requestMethod)))
                .findFirst()
                .orElseThrow(() -> new ServletException("not found handler " + requestMethod + " : " + requestUri));

        /**
         * handler 를 (여기서는 Object or Controller 타입) 실행시켜줄 수 있는 적절한 handler adapter 를 찾는다.
         * handler adapter 에게 handler 를 실행시키고 ModelAndView 를 반환받는다.
         */
        HandlerAdapter handlerAdapter = handlerAdapters.stream()
                .filter(ha -> ha.supports(handler))
                .findFirst()
                .orElseThrow(() -> new ServletException("not found supported handler " + handler + " : " + requestUri));

        try {
            ModelAndView modelAndView = handlerAdapter.handle(request, response, handler);
            View view = modelAndView.getView();
            view.render(modelAndView.getModel(), request, response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}

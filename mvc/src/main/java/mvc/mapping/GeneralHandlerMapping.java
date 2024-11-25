package mvc.mapping;

import mvc.HandlerKey;
import mvc.RequestMethod;
import mvc.controller.Controller;
import mvc.controller.HomeController;
import mvc.controller.JsonController;
import mvc.controller.ListUserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class GeneralHandlerMapping implements HandlerMapping {
    private static final Logger log = LoggerFactory.getLogger(GeneralHandlerMapping.class);

    private final Map<HandlerKey, Controller> mappings = new HashMap<>();

    public void initMappings() {
        setUpController(new HandlerKey("/home", RequestMethod.GET), new HomeController());
        setUpController(new HandlerKey("/list", RequestMethod.GET), new ListUserController());
        setUpController(new HandlerKey("/api/json", RequestMethod.GET), new JsonController());

        log.debug("[GeneralHandlerMapping] register handlerExecution : {}", new HandlerKey("/home", RequestMethod.GET));
        log.debug("[GeneralHandlerMapping] register handlerExecution : {}", new HandlerKey("/list", RequestMethod.GET));
        log.debug("[GeneralHandlerMapping] register handlerExecution : {}", new HandlerKey("/api/json", RequestMethod.GET));
    }

    @Override
    public Controller findHandler(HandlerKey handlerKey) {
        return mappings.get(handlerKey);
    }

    private void setUpController(HandlerKey handlerKey, Controller controller) {
        mappings.put(handlerKey, controller);
    }
}

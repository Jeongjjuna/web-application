package mvc.mapping;

import mvc.HandlerKey;
import mvc.RequestMethod;
import mvc.controller.Controller;
import mvc.controller.HomeController;
import mvc.controller.JsonController;
import mvc.controller.ListUserController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GeneralHandlerMapping implements HandlerMapping {

    private final Map<HandlerKey, Controller> mappings = new HashMap<>();

    public void initMappings() {
        setUpController(new HandlerKey("/home", RequestMethod.GET), new HomeController());
        setUpController(new HandlerKey("/list", RequestMethod.GET), new ListUserController());
        setUpController(new HandlerKey("/api/json", RequestMethod.GET), new JsonController());
    }

    @Override
    public Controller findHandler(HandlerKey handlerKey) {
        return mappings.get(handlerKey);
    }

    private Optional<Controller> findControllerByUrl(HandlerKey handlerKey) {
        return Optional.ofNullable(mappings.get(handlerKey));
    }

    private void setUpController(HandlerKey handlerKey, Controller controller) {
        mappings.put(handlerKey, controller);
    }
}

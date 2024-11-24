package mvc.mapping;

import mvc.controller.Contoller;
import mvc.controller.HomeController;
import mvc.controller.JsonController;
import mvc.controller.ListUserController;
import mvc.controller.NotFoundController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RequestMapping {

    private final Contoller notFoundController = new NotFoundController();
    private final Map<String, Contoller> mappings = new HashMap<>();

    public RequestMapping() {
        initMappings();
    }

    public Contoller getController(String url) {
        return findControllerByUrl(url)
                .orElse(notFoundController);
    }

    private Optional<Contoller> findControllerByUrl(String url) {
        return Optional.ofNullable(mappings.get(url));
    }

    private void initMappings() {
        setUpController("/home", new HomeController());
        setUpController("/list", new ListUserController());
        setUpController("/api/json", new JsonController());
    }

    private void setUpController(String url, Contoller controller) {
        mappings.put(url, controller);
    }
}

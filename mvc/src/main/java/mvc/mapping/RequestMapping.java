package mvc.mapping;

import mvc.controller.HomeController;
import mvc.controller.InterfaceController;
import mvc.controller.JsonController;
import mvc.controller.ListUserController;
import mvc.controller.NotFoundController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RequestMapping {

    private final InterfaceController notFoundController = new NotFoundController();
    private final Map<String, InterfaceController> mappings = new HashMap<>();

    public RequestMapping() {
        initMappings();
    }

    public InterfaceController getController(String url) {
        return findControllerByUrl(url)
                .orElse(notFoundController);
    }

    private Optional<InterfaceController> findControllerByUrl(String url) {
        return Optional.ofNullable(mappings.get(url));
    }

    private void initMappings() {
        setUpController("/home", new HomeController());
        setUpController("/list", new ListUserController());
        setUpController("/json", new JsonController());
    }

    private void setUpController(String url, InterfaceController controller) {
        mappings.put(url, controller);
    }
}

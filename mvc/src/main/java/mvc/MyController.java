package mvc;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mvc.annotation.Controller;
import mvc.annotation.RequestMapping;
import mvc.model.User;
import mvc.view.JsonView;
import mvc.view.ModelAndView;
import mvc.view.ThymeleafView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class MyController {
    private static final Logger log = LoggerFactory.getLogger(MyController.class);

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView home(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(new ThymeleafView("home"))
                .addObject("message", "model data");
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(new ThymeleafView("redirect:/home"));
    }

    @RequestMapping(value = "/api/json", method = RequestMethod.GET)
    public ModelAndView json(HttpServletRequest request, HttpServletResponse response) {
        User userModel = new User("name", 10);
        return new ModelAndView(new JsonView())
                .addObject("user", userModel);
    }
}

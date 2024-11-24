package mvc.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.util.Map;

public class JsonView implements View {

    public static final String CONTENT_TYPE_JSON = "application/json;charset=UTF-8";

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType(CONTENT_TYPE_JSON);
        PrintWriter out = response.getWriter();
        out.print(mapper.writeValueAsString(model));
    }
}

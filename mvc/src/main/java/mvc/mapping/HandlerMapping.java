package mvc.mapping;

public interface HandlerMapping {
    Object findHandler(HandlerKey handlerKey);
}

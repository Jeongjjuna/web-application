package mvc.mapping;

import mvc.HandlerKey;

public interface HandlerMapping {
    Object findHandler(HandlerKey handlerKey);
}

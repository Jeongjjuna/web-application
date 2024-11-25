package mvc.mapping;

import mvc.HandlerKey;

public class AnnotationHandlerMapping implements HandlerMapping {

    public void initMappings() {
        // TODO : 어노테이션과 리플렉션을 활용해서 맵핑테이블에 등록해준다.
    }

    @Override
    public Object findHandler(HandlerKey handlerKey) {
        return null;
    }
}

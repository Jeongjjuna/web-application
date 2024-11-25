package mvc.mapping;

import mvc.HandlerExecution;
import mvc.HandlerKey;
import mvc.annotation.Controller;
import mvc.annotation.RequestMapping;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AnnotationHandlerMapping implements HandlerMapping {
    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private final Object[] basePackage;
    private final Map<HandlerKey, HandlerExecution> handlerExecutions = new HashMap<>();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initMappings() {
        Reflections reflections = new Reflections(basePackage);

        Set<Class<?>> preInitiatedControllers = reflections.getTypesAnnotatedWith(Controller.class, true);

        Map<Class<?>, Object> controllerInstanceMapper = createControllerInstanceMapper(preInitiatedControllers);

        Set<Method> methods = findAllMethod(preInitiatedControllers);

        methods.forEach(method -> registerHandlerExecution(method, controllerInstanceMapper));
    }

    private void registerHandlerExecution(Method method, Map<Class<?>, Object> controllerInstanceMapper) {
        RequestMapping rm = method.getAnnotation(RequestMapping.class);

        HandlerKey handlerKey = new HandlerKey(rm.value(), rm.method());
        handlerExecutions.put(
                handlerKey,
                new HandlerExecution(controllerInstanceMapper.get(method.getDeclaringClass()), method)
        );

        log.debug("[AnnotationHandlerMapping] register handlerExecution : {}", handlerKey);
    }

    private Set<Method> findAllMethod(Set<Class<?>> preInitiatedControllers) {
        Set<Method> methods = new HashSet<>();
        for (Class<?> clazz : preInitiatedControllers) {
            methods.addAll(ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withAnnotation(RequestMapping.class)));
        }
        return methods;
    }

    private Map<Class<?>, Object> createControllerInstanceMapper(Set<Class<?>> preInitiatedControllers) {
        Map<Class<?>, Object> controllerInstanceMapper = new HashMap<>();
        try {
            for (Class<?> clazz : preInitiatedControllers) {
                controllerInstanceMapper.put(clazz, clazz.newInstance());
            }
        } catch (InstantiationException | IllegalAccessException e) {
            log.error(e.getMessage());
        }
        return controllerInstanceMapper;
    }

    @Override
    public Object findHandler(HandlerKey handlerKey) {
        return handlerExecutions.get(handlerKey);
    }

}

package core.nmvc;

import com.google.common.collect.Maps;
import core.annotation.Controller;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

public class ControllerScanner {
    private Logger log = LoggerFactory.getLogger(ControllerScanner.class);

    private Reflections reflections;

    public ControllerScanner(Object... basePackage) {
        reflections =  new Reflections(basePackage);
    }

    public Map<Class<?>, Object> getControllers() {
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Controller.class);
        return instantiateController(annotated);
    }

    Map<Class<?>, Object> instantiateController(Set<Class<?>> annotated) {
        Map<Class<?>, Object> controllers = Maps.newHashMap();
        try {
            for (Class<?> clazz : annotated) {
                controllers.put(clazz, clazz.getConstructor().newInstance());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return controllers;
    }
}

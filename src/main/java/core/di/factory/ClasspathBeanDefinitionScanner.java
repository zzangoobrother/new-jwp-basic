package core.di.factory;

import com.google.common.collect.Sets;
import core.annotation.*;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Set;

public class ClasspathBeanDefinitionScanner {
    private final BeanDefinitionRegistry beanDefinitionRegistry;

    public ClasspathBeanDefinitionScanner(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    public void doScan(Object... basePackages) {
        Reflections reflections = new Reflections(basePackages);
        Set<Class<?>> beanClasses = getTypesAnnotatedWith(reflections, Controller.class, Service.class, Repository.class, Component.class, Configuration.class);
        for (Class<?> clazz : beanClasses) {
            beanDefinitionRegistry.registerBeanDefinition(clazz, new BeanDefinition(clazz));
        }
    }

    private Set<Class<?>> getTypesAnnotatedWith(Reflections reflections, Class<? extends Annotation>... annotations) {
        Set<Class<?>> preInstantiatedBeans = Sets.newHashSet();
        for (Class<? extends Annotation> annotation : annotations) {
            preInstantiatedBeans.addAll(reflections.getTypesAnnotatedWith(annotation));
        }
        return preInstantiatedBeans;
    }
}

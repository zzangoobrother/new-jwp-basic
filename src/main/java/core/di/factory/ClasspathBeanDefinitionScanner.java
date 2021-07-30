package core.di.factory;

import com.google.common.collect.Sets;
import core.annotation.Component;
import core.annotation.Controller;
import core.annotation.Repository;
import core.annotation.Service;
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
        System.out.println("ClasspathBeanDefinitionScanner doScan start : " + reflections.getTypesAnnotatedWith(Controller.class));
        System.out.println("ClasspathBeanDefinitionScanner doScan start : " + reflections.getTypesAnnotatedWith(Service.class));
        System.out.println("ClasspathBeanDefinitionScanner doScan start : " + reflections.getTypesAnnotatedWith(Repository.class));
        System.out.println("ClasspathBeanDefinitionScanner doScan start : " + reflections.getTypesAnnotatedWith(Component.class));
        Set<Class<?>> beanClasses = getTypesAnnotatedWith(reflections, Controller.class, Service.class, Repository.class, Component.class);
        for (Class<?> clazz : beanClasses) {
            beanDefinitionRegistry.registerBeanDefinition(clazz, new BeanDefinition(clazz));
        }
    }

    private Set<Class<?>> getTypesAnnotatedWith(Reflections reflections, Class<? extends Annotation>... annotations) {
        Set<Class<?>> preInstantiatedBeans = Sets.newHashSet();
        for (Class<? extends Annotation> annotation : annotations) {
            preInstantiatedBeans.addAll(reflections.getTypesAnnotatedWith(annotation));
            System.out.println("ClasspathBeanDefinitionScanner getTypesAnnotatedWith annotation start : " + annotation);
            System.out.println("ClasspathBeanDefinitionScanner getTypesAnnotatedWith start : " + reflections.getTypesAnnotatedWith(annotation));
        }
        return preInstantiatedBeans;
    }
}

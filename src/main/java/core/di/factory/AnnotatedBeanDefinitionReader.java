package core.di.factory;

import core.annotation.Bean;

import java.lang.reflect.Method;
import java.util.Set;

public class AnnotatedBeanDefinitionReader {
    private BeanDefinitionRegistry beanDefinitionRegistry;

    public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    public void register(Class<?>... annotatedClasses) {
        for (Class<?> annotatedClass : annotatedClasses) {
            registerBean(annotatedClass);
        }
    }

    public void registerBean(Class<?> annotatedClass) {
        beanDefinitionRegistry.registerBeanDefinition(annotatedClass, new BeanDefinition(annotatedClass));
        Set<Method> beanMethods = BeanFactoryUtils.getBeanMethods(annotatedClass, Bean.class);
        for (Method beanMethod : beanMethods) {
            AnnotatedBeanDefinition annotatedBeanDefinition = new AnnotatedBeanDefinition(beanMethod.getReturnType(), beanMethod);
            beanDefinitionRegistry.registerBeanDefinition(beanMethod.getReturnType(), annotatedBeanDefinition);
        }
    }
}

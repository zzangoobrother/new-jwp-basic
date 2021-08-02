package core.di.factory;

public interface BeanDefinitionRegistry {
    void registerBeanDefinition(Class<?> clazz, DefaultBeanDefinition beanDefinition);
}

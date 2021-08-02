package core.di.factory;

import com.google.common.collect.Sets;

import java.util.Set;

public class ConstructorInjector extends AbstractInjector {
    public ConstructorInjector(DefaultBeanFactory defaultBeanFactory) {
        super(defaultBeanFactory);
    }

    @Override
    Set<?> getInjectedBeans(Class<?> clazz) {
        return Sets.newHashSet();
    }

    @Override
    Class<?> getBeanClass(Object injectedBean) {
        return null;
    }

    @Override
    void inject(Object injectedBean, Object bean, DefaultBeanFactory defaultBeanFactory) {

    }
}

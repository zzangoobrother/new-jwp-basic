package core.di.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Set;

public class FieldInjector extends AbstractInjector {
    private Logger log = LoggerFactory.getLogger(FieldInjector.class);

    public FieldInjector(DefaultBeanFactory defaultBeanFactory) {
        super(defaultBeanFactory);
    }

    @Override
    Set<?> getInjectedBeans(Class<?> clazz) {
        return BeanFactoryUtils.getInjectedFields(clazz);
    }

    @Override
    Class<?> getBeanClass(Object injectedBean) {
        Field field = (Field) injectedBean;
        return field.getType();
    }

    @Override
    void inject(Object injectedBean, Object bean, DefaultBeanFactory defaultBeanFactory) {
        Field field = (Field) injectedBean;
        try {
            field.setAccessible(true);
            field.set(defaultBeanFactory.getBean(field.getDeclaringClass()), bean);
        } catch (IllegalAccessException | IllegalArgumentException e) {
            log.error(e.getMessage());
        }
    }
}

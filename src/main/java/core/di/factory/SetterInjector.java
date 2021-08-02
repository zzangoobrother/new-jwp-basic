package core.di.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class SetterInjector extends AbstractInjector {
    private Logger log = LoggerFactory.getLogger(SetterInjector.class);

    public SetterInjector(DefaultBeanFactory defaultBeanFactory) {
        super(defaultBeanFactory);
    }

    @Override
    Set<?> getInjectedBeans(Class<?> clazz) {
        return BeanFactoryUtils.getInjectedMethods(clazz);
    }

    @Override
    Class<?> getBeanClass(Object injectedBean) {
        Method method = (Method) injectedBean;
        log.debug("invoke method : {}", method);
        Class<?>[] paramTypes = method.getParameterTypes();
        if (paramTypes.length != 1) {
            throw new IllegalStateException("DI할 메소드 인자는 하나여야 합니다.");
        }

        return paramTypes[0];
    }

    @Override
    void inject(Object injectedBean, Object bean, DefaultBeanFactory defaultBeanFactory) {
        Method method = (Method) injectedBean;
        try {
            method.invoke(defaultBeanFactory.getBean(method.getDeclaringClass()), bean);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            log.error(e.getMessage());
        }
    }
}

package core.di.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public abstract class AbstractInjector implements Injector {
    private Logger log = LoggerFactory.getLogger(AbstractInjector.class);

    private DefaultBeanFactory defaultBeanFactory;

    public AbstractInjector(DefaultBeanFactory defaultBeanFactory) {
        this.defaultBeanFactory = defaultBeanFactory;
    }

    @Override
    public void inject(Class<?> clazz) {
//        instantiateClass(clazz);
//        Set<?> injectedBeans = getInjectedBeans(clazz);
//        for (Object injectedBean : injectedBeans) {
//            Class<?> beanClass = getBeanClass(injectedBean);
//            inject(injectedBean, instantiateClass(beanClass), beanFactory);
//        }
    }

    abstract Set<?> getInjectedBeans(Class<?> clazz);

    abstract Class<?> getBeanClass(Object injectedBean);

    abstract void inject(Object injectedBean, Object bean, DefaultBeanFactory defaultBeanFactory);

//    private Object instantiateClass(Class<?> clazz) {
//        Class<?> concreteClass = findBeanClass(clazz, beanFactory.getPreInstanticateBeans());
//        Object bean = beanFactory.getBean(clazz);
//        if (bean != null) {
//            return bean;
//        }
//
//        Constructor<?> injectedConstructor = BeanFactoryUtils.getInjectedConstructor(concreteClass);
//        if (injectedConstructor == null) {
//            bean = BeanUtils.instantiate(concreteClass);
//            beanFactory.registerBean(concreteClass, bean);
//            return bean;
//        }
//
//        log.debug("Constructor : {}", injectedConstructor);
//        bean = instantiateConstructor(injectedConstructor);
//        beanFactory.registerBean(concreteClass, bean);
//        return bean;
//    }

//    private Object instantiateConstructor(Constructor<?> constructor) {
//        Class<?>[] pTypes = constructor.getParameterTypes();
//        List<Object> args = Lists.newArrayList();
//        for (Class<?> clazz : pTypes) {
//            Class<?> concreteClazz = findBeanClass(clazz, beanFactory.getPreInstanticateBeans());
//            Object bean = beanFactory.getBean(concreteClazz);
//            if (bean == null) {
//                bean = instantiateClass(concreteClazz);
//            }
//            args.add(bean);
//        }
//        return BeanUtils.instantiateClass(constructor, args.toArray());
//    }
//
//    private Class<?> findBeanClass(Class<?> clazz, Set<Class<?>> preInstanticateBeans) {
//        Class<?> concreteClazz = BeanFactoryUtils.findConcreteClass(clazz, preInstanticateBeans);
//        if (!preInstanticateBeans.contains(concreteClazz)) {
//            throw new IllegalStateException(clazz + "는 Bean이 아니다.");
//        }
//        return concreteClazz;
//    }
}

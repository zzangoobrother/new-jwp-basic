package core.di.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Set;

public interface BeanDefinition {
    Constructor<?> getInjectConstructor();

    Set<Field> getInjectFields();

    Class<?> getBeanClazz();

    InjectType getResolvedInjectMode();
}

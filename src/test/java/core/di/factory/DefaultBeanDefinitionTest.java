package core.di.factory;

import core.di.factory.example.JdbcUserRepository;
import core.di.factory.example.MyQnaService;
import core.di.factory.example.MyUserController;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Set;

import static org.junit.Assert.*;

public class DefaultBeanDefinitionTest {
    private static final Logger log = LoggerFactory.getLogger(DefaultBeanDefinition.class);

    @Test
    public void getResolvedAutowireMode() {
        DefaultBeanDefinition beanDefinition = new DefaultBeanDefinition(JdbcUserRepository.class);
        assertEquals(InjectType.INJECT_NO, beanDefinition.getResolvedInjectMode());

        beanDefinition = new DefaultBeanDefinition(MyUserController.class);
        assertEquals(InjectType.INJECT_FIELD, beanDefinition.getResolvedInjectMode());

        beanDefinition = new DefaultBeanDefinition(MyQnaService.class);
        assertEquals(InjectType.INJECT_CONSTRUCTOR, beanDefinition.getResolvedInjectMode());

    }

    @Test
    public void getInjectProperties() throws Exception {
        DefaultBeanDefinition beanDefinition = new DefaultBeanDefinition(MyUserController.class);
        Set<Field> injectFields = beanDefinition.getInjectFields();
        for (Field field : injectFields) {
            log.debug("inject field : {}", field);
        }
    }

    @Test
    public void getConstructor() throws Exception {
        DefaultBeanDefinition beanDefinition = new DefaultBeanDefinition(MyQnaService.class);
        Set<Field> injectFields = beanDefinition.getInjectFields();
        assertEquals(0, injectFields.size());
        Constructor<?> constructor = beanDefinition.getInjectConstructor();
        log.debug("inject constructor : {}", constructor);
    }
}
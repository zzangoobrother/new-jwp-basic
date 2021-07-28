package core.di.factory;

import core.di.factory.example.JdbcUserRepository;
import core.di.factory.example.MyQnaService;
import core.di.factory.example.MyUserController;
import core.nmvc.MyController;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Set;

import static org.junit.Assert.*;

public class BeanDefinitionTest {
    private static final Logger log = LoggerFactory.getLogger(BeanDefinition.class);

    @Test
    public void getResolvedAutowireMode() {
        BeanDefinition beanDefinition = new BeanDefinition(JdbcUserRepository.class);
        assertEquals(InjectType.INJECT_NO, beanDefinition.getResolvedInjectMode());

        beanDefinition = new BeanDefinition(MyUserController.class);
        assertEquals(InjectType.INJECT_FIELD, beanDefinition.getResolvedInjectMode());

        beanDefinition = new BeanDefinition(MyQnaService.class);
        assertEquals(InjectType.INJECT_CONSTRUCTOR, beanDefinition.getResolvedInjectMode());

    }

    @Test
    public void getInjectProperties() throws Exception {
        BeanDefinition beanDefinition = new BeanDefinition(MyUserController.class);
        Set<Field> injectFields = beanDefinition.getInjectFields();
        for (Field field : injectFields) {
            log.debug("inject field : {}", field);
        }
    }

    @Test
    public void getConstructor() throws Exception {
        BeanDefinition beanDefinition = new BeanDefinition(MyQnaService.class);
        Set<Field> injectFields = beanDefinition.getInjectFields();
        assertEquals(0, injectFields.size());
        Constructor<?> constructor = beanDefinition.getInjectConstructor();
        log.debug("inject constructor : {}", constructor);
    }
}
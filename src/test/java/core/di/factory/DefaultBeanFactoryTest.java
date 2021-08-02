package core.di.factory;

import com.google.common.collect.Sets;
import core.di.context.annotation.ClasspathBeanDefinitionScanner;
import core.di.factory.example.MyQnaService;
import core.di.factory.example.MyUserController;
import core.di.factory.example.MyUserService;
import core.di.factory.example.QnaController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Set;

import static org.junit.Assert.assertNotNull;

public class DefaultBeanFactoryTest {
    private Reflections reflections;
    private DefaultBeanFactory defaultBeanFactory;

    @Before
    public void init() {
        //reflections = new Reflections("core.di.factory.example");
        defaultBeanFactory = new DefaultBeanFactory();
        ClasspathBeanDefinitionScanner scanner = new ClasspathBeanDefinitionScanner(defaultBeanFactory);
        scanner.doScan("core.di.factory.example");
        defaultBeanFactory.preInstantiateSinglonetons();
    }

    private Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation>... annotations) {
        Set<Class<?>> beans = Sets.newHashSet();
        for (Class<? extends Annotation> annotation : annotations) {
            beans.addAll(reflections.getTypesAnnotatedWith(annotation));
        }
        return beans;
    }

    @Test
    public void di() throws Exception {
        QnaController qnaController = defaultBeanFactory.getBean(QnaController.class);

        assertNotNull(qnaController);
        assertNotNull(qnaController.getMyQnaService());

        MyQnaService myQnaService = qnaController.getMyQnaService();
        assertNotNull(myQnaService.getUserRepository());
        assertNotNull(myQnaService.getQuestionRepository());
    }

    @Test
    public void fieldDi() throws Exception {
        MyUserService myUserService = defaultBeanFactory.getBean(MyUserService.class);
        assertNotNull(myUserService);
        assertNotNull(myUserService.getUserRepository());
    }

    @Test
    public void setterDi() throws Exception {
        MyUserController myUserController = defaultBeanFactory.getBean(MyUserController.class);

        assertNotNull(myUserController);
        assertNotNull(myUserController.getMyUserService());
    }

    @After
    public void tearDown() {
        defaultBeanFactory.clear();
    }
}

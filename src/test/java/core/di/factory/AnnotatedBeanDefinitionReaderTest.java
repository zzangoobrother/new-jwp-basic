package core.di.factory;

import core.di.factory.example.JdbcUserRepository;
import di.examples.ExampleConfig;
import di.examples.IntegrationConfig;
import di.examples.MyJdbcTemplate;
import org.junit.Test;

import javax.sql.DataSource;

import static org.junit.Assert.assertNotNull;

public class AnnotatedBeanDefinitionReaderTest {
    @Test
    public void register_simple() {
        BeanFactory beanFactory = new BeanFactory();
        AnnotatedBeanDefinitionReader abdr = new AnnotatedBeanDefinitionReader(beanFactory);
        abdr.register(ExampleConfig.class);
        beanFactory.initialize();
        
        assertNotNull(beanFactory.getBean(DataSource.class));
    }

    @Test
    public void register_ClasspathBeanDefinitionScanner_통합() {
        BeanFactory beanFactory = new BeanFactory();
        AnnotatedBeanDefinitionReader beanDefinitionReader = new AnnotatedBeanDefinitionReader(beanFactory);
        beanDefinitionReader.register(IntegrationConfig.class);

        ClasspathBeanDefinitionScanner classpathBeanDefinitionScanner = new ClasspathBeanDefinitionScanner(beanFactory);
        classpathBeanDefinitionScanner.doScan("core.di.factory.example");

        beanFactory.initialize();

        assertNotNull(beanFactory.getBean(DataSource.class));

        JdbcUserRepository userRepository = beanFactory.getBean(JdbcUserRepository.class);
        assertNotNull(userRepository);
        assertNotNull(userRepository.getDataSource());

        MyJdbcTemplate myJdbcTemplate = beanFactory.getBean(MyJdbcTemplate.class);
        assertNotNull(myJdbcTemplate);
        assertNotNull(myJdbcTemplate.getDataSource());
    }
}

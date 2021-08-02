package core.di.factory;

import core.di.context.annotation.AnnotatedBeanDefinitionReader;
import core.di.context.annotation.ClasspathBeanDefinitionScanner;
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
        DefaultBeanFactory defaultBeanFactory = new DefaultBeanFactory();
        AnnotatedBeanDefinitionReader abdr = new AnnotatedBeanDefinitionReader(defaultBeanFactory);
        abdr.loadBeanDefinitions(ExampleConfig.class);
        defaultBeanFactory.preInstantiateSinglonetons();
        
        assertNotNull(defaultBeanFactory.getBean(DataSource.class));
    }

    @Test
    public void register_ClasspathBeanDefinitionScanner_통합() {
        DefaultBeanFactory defaultBeanFactory = new DefaultBeanFactory();
        AnnotatedBeanDefinitionReader beanDefinitionReader = new AnnotatedBeanDefinitionReader(defaultBeanFactory);
        beanDefinitionReader.loadBeanDefinitions(IntegrationConfig.class);

        ClasspathBeanDefinitionScanner classpathBeanDefinitionScanner = new ClasspathBeanDefinitionScanner(defaultBeanFactory);
        classpathBeanDefinitionScanner.doScan("core.di.factory.example");

        defaultBeanFactory.preInstantiateSinglonetons();

        assertNotNull(defaultBeanFactory.getBean(DataSource.class));

        JdbcUserRepository userRepository = defaultBeanFactory.getBean(JdbcUserRepository.class);
        assertNotNull(userRepository);
        assertNotNull(userRepository.getDataSource());

        MyJdbcTemplate myJdbcTemplate = defaultBeanFactory.getBean(MyJdbcTemplate.class);
        assertNotNull(myJdbcTemplate);
        assertNotNull(myJdbcTemplate.getDataSource());
    }
}

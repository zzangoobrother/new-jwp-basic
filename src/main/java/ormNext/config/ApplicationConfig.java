package ormNext.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

@Configuration
@Import(value = {PersistenceJPAConfig.class})
@ComponentScan(basePackages = {"ormNext.service", "ormNext.support"})
public class ApplicationConfig {
    private Logger log = LoggerFactory.getLogger(ApplicationConfig.class);

    @Autowired
    private Environment env;

    @PostConstruct
    public void initApp() {
        log.debug("Loking for Spring profiles...");
        if (env.getActiveProfiles().length == 0) {
            log.info("No Spring profile configured, running with default configuration.");
        } else {
            for (String profile : env.getActiveProfiles()) {
                log.info("Detected Spring profile: {}", profile);
            }
        }
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}

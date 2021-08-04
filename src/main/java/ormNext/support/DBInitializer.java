package ormNext.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ormNext.model.User;
import ormNext.repository.UserRepository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Component
public class DBInitializer {
    private static final Logger log = LoggerFactory.getLogger(DBInitializer.class);

    @Inject
    private UserRepository userRepository;

    @PostConstruct
    public void initialize() {
        System.out.println("DBInitializer");
        User user = new User("admin", "test", "name", "sss@naver.com");
        userRepository.save(user);
        log.info("DB Initialized Success!!");
    }
}

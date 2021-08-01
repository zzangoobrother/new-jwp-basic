package next.support.context;

import core.annotation.Component;
import core.annotation.Inject;
import core.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Component
public class DBInitializer {
    private static final Logger logger = LoggerFactory.getLogger(DBInitializer.class);

    @Inject
    private DataSource dataSource;

    @PostConstruct
    public void initialize() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        populator.setSqlScriptEncoding("UTF-8");
        DatabasePopulatorUtils.execute(populator, dataSource);

        logger.info("Completed Load ServletContext!");
    }
}

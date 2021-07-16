package core.nmvc;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ControllerScannerTest {
    private static final Logger log = LoggerFactory.getLogger(ControllerScanner.class);
    private ControllerScanner controllerScanner;

    @Before
    public void setup() {
        controllerScanner =new ControllerScanner("core.nmvc");
    }

    @Test
    public void getControllers() throws Exception {
        Map<Class<?>, Object> controllers = controllerScanner.getControllers();
        for (Class<?> controller : controllers.keySet()) {
            log.debug("controller : {}", controller);
        }
    }
}
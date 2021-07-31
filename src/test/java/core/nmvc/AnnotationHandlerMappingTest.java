package core.nmvc;

import core.di.factory.AnnotationConfigApplicationContext;
import core.mvc.ModelAndView;
import next.config.MyConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.Assert.*;

public class AnnotationHandlerMappingTest {
    private AnnotationHandlerMapping handlerMapping;
    private MockHttpServletResponse response;

    @Before
    public void init() {
        handlerMapping = new AnnotationHandlerMapping(new AnnotationConfigApplicationContext(MyConfiguration.class));
        handlerMapping.initialize();

        response = new MockHttpServletResponse();
    }

    @Test
    public void list() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/users");
        HandlerExecution handler = handlerMapping.getHandler(request);
        ModelAndView mav = handler.execute(request, response);
        mav.getView().render(mav.getModel(), request, response);
        assertEquals("/users/list.jsp", response.getForwardedUrl());
    }
}
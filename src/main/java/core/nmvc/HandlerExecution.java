package core.nmvc;

import core.mvc.Controller;
import core.view.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HandlerExecution implements Controller {
    private Logger log = LoggerFactory.getLogger(HandlerExecution.class);

    private Object declaredObject;
    private Method method;

    public HandlerExecution(Object declaredObject, Method method) {
        this.declaredObject = declaredObject;
        this.method = method;
    }

    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            return (ModelAndView) method.invoke(declaredObject, request, response);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            log.error("{} method invoke fail. error message : {}", method, e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

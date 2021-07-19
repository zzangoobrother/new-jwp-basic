package core.nmvc;

import core.mvc.Controller;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {
    Controller getHandler(HttpServletRequest request);
}

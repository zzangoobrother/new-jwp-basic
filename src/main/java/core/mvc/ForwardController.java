package core.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(ForwardController.class);
    private String url;

    public ForwardController(String url) {
        this.url = url;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return url;
    }
}

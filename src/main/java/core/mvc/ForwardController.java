package core.mvc;

import core.view.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(ForwardController.class);
    private String url;

    public ForwardController(String url) {
        this.url = url;
    }

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return jspView(url);
    }
}

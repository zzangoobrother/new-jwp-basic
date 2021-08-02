package core.nmvc;

import core.view.JsonView;
import core.view.JspView;
import core.view.ModelAndView;

public class AbstractNewController {
    protected ModelAndView jspView(String forwardUrl) {
        return new ModelAndView(new JspView(forwardUrl));
    }

    protected ModelAndView jsonView() {
        return new ModelAndView(new JsonView());
    }
}

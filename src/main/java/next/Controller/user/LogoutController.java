package next.Controller.user;

import core.mvc.Controller;
import core.mvc.JspView;
import core.mvc.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutController implements Controller {
    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        return new JspView("redirect:/");
    }
}

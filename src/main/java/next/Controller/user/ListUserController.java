package next.Controller.user;

import core.mvc.Controller;
import core.mvc.JspView;
import core.mvc.View;
import next.dao.UserDao;
import next.web.UserSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListUserController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(ListUserController.class);

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!UserSessionUtils.isLogin(request.getSession())) {
            return new JspView("redirecct:/users/loginForm");
        }

        UserDao userDao = new UserDao();
        request.setAttribute("users", userDao.findAll());
        return new JspView("/user/list.jsp");
    }
}

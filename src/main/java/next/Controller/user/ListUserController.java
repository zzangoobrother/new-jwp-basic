package next.Controller.user;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.UserDao;
import next.web.UserSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListUserController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(ListUserController.class);

    private UserDao userDao;

    public ListUserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!UserSessionUtils.isLogin(request.getSession())) {
            return jspView("redirecct:/users/loginForm");
        }

        return jspView("/user/list.jsp").addObject("users", userDao.findAll());
    }
}

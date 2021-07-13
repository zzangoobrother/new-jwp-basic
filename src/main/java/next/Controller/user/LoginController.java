package next.Controller.user;

import core.db.DataBase;
import core.mvc.*;
import next.dao.UserDao;
import next.model.User;
import next.web.UserSessionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginController extends AbstractController {
    private UserDao userDao = UserDao.getInstance();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User loginUser = userDao.findByUserId(request.getParameter("userId"));

        if (loginUser == null) {
            return jspView("/users/loginForm").addObject("loginFailed", true);
        }

        if (loginUser.matchPassword(request.getParameter("password"))) {
            HttpSession session = request.getSession();
            session.setAttribute(UserSessionUtils.USER_SESSION_KEY, loginUser);
            return jspView("redirect:/");
        } else {
            request.setAttribute("loginFailed", true);
            return jspView("/users/loginForm").addObject("loginFailed", true);
        }

    }
}

package next.Controller;

import core.db.DataBase;
import core.mvc.Controller;
import next.dao.UserDao;
import next.model.User;
import next.web.UserSessionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserDao userDao = new UserDao();
        User loginUser = userDao.findByUserId(request.getParameter("userId"));

        if (loginUser == null) {
            request.setAttribute("loginFailed", true);
            return "/users/loginForm";
        }

        if (loginUser.matchPassword(request.getParameter("password"))) {
            HttpSession session = request.getSession();
            session.setAttribute(UserSessionUtils.USER_SESSION_KEY, loginUser);
            return "redirect:/";
        } else {
            request.setAttribute("loginFailed", true);
            return "/users/loginForm";
        }

    }
}

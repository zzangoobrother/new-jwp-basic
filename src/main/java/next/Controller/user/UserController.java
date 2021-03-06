package next.Controller.user;

import core.annotation.Controller;
import core.annotation.Inject;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.db.DataBase;
import core.view.ModelAndView;
import core.nmvc.AbstractNewController;
import next.dao.UserDao;
import next.model.User;
import next.web.UserSessionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class UserController extends AbstractNewController {
    private UserDao userDao;

    @Inject
    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping("/users")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!UserSessionUtils.isLogin(request.getSession())) {
            return jspView("redirecct:/users/loginForm");
        }

        return jspView("/user/list.jsp").addObject("users", userDao.findAll());
    }

    @RequestMapping("/users/form")
    public ModelAndView form(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return jspView("/user/form.jsp");
    }

    @RequestMapping(value = "/users/create", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = new User(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email")
        );

        userDao.insert(user);
        return jspView("redirect:/");
    }

    @RequestMapping("/users/loginForm")
    public ModelAndView loginForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return jspView("/user/login.jsp");
    }

    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
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

    @RequestMapping("/users/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        return jspView("redirect:/");
    }

    @RequestMapping("/users/updateForm")
    public ModelAndView updateForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = request.getParameter("userId");
        User user = userDao.findByUserId(userId);

        if (!UserSessionUtils.isSameUser(request.getSession(), user)) {
            throw new IllegalStateException("?????? ????????? ????????? ????????? ??? ????????????.");
        }
        return jspView("/user/update.jsp").addObject("user", user);
    }

    @RequestMapping(value = "/users/update", method = RequestMethod.POST)
    public ModelAndView update(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = userDao.findByUserId(request.getParameter("userId"));
        if (!UserSessionUtils.isSameUser(request.getSession(), user)) {
            throw new IllegalStateException("?????? ????????? ????????? ????????? ??? ????????????.");
        }

        User updateUser = new User(request.getParameter("userId"), request.getParameter("password"),
                request.getParameter("name"), request.getParameter("email"));

        userDao.update(updateUser);
        return jspView("redirect:/");
    }

    @RequestMapping("/users/profile")
    public ModelAndView profile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = request.getParameter("userId");
        User user = DataBase.findUserById(userId);
        if (user == null) {
            throw new NullPointerException("???????????? ?????? ??? ????????????.");
        }

        return jspView("/user/profile.jsp").addObject("user", user);
    }
}

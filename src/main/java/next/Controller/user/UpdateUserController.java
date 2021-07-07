package next.Controller.user;

import core.mvc.Controller;
import core.mvc.JspView;
import core.mvc.View;
import next.dao.UserDao;
import next.model.User;
import next.web.UserSessionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateUserController implements Controller {
    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserDao userDao = new UserDao();

        User user = userDao.findByUserId(request.getParameter("userId"));
        if (!UserSessionUtils.isSameUser(request.getSession(), user)) {
            throw new IllegalStateException("다른 사용자 정보를 수정할 수 없습니다.");
        }

        User updateUser = new User(request.getParameter("userId"), request.getParameter("password"),
                request.getParameter("name"), request.getParameter("email"));

        userDao.update(updateUser);
        return new JspView("redirect:/");
    }
}

package next.Controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.JdbcQuestionDao;
import next.model.Question;
import next.web.UserSessionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateFormQuestionController extends AbstractController {
    private JdbcQuestionDao jdbcQuestionDao = JdbcQuestionDao.getInstance();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!UserSessionUtils.isLogin(request.getSession())) {
            return jspView("redirect:/users/loginForm");
        }

        long questionId = Long.parseLong(request.getParameter("questionId"));
        Question question = jdbcQuestionDao.findById(questionId);
        if (!question.isSameUser(UserSessionUtils.getUserFromSession(request.getSession()))) {
            throw new IllegalStateException("다른 사용자가 쓴 글을 수정할 수 없습니다.");
        }

        return jspView("/qna/update.jsp").addObject("question", question);
    }
}

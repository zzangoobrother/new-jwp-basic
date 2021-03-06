package next.Controller.qna;

import core.mvc.AbstractController;
import core.view.ModelAndView;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.User;
import next.web.UserSessionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateQuestionController extends AbstractController {
    private QuestionDao questionDao;

    public CreateQuestionController(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!UserSessionUtils.isLogin(request.getSession())) {
            return jspView("redirect:/users/loginForm");
        }
        User user = UserSessionUtils.getUserFromSession(request.getSession());
        Question question = new Question(user.getUserId(), request.getParameter("title"),
                            request.getParameter("contents"));
        questionDao.insert(question);
        return jspView("redirect:/");
    }
}

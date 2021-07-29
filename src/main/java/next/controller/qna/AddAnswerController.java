package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Result;
import next.web.UserSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddAnswerController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(AddAnswerController.class);

    private QuestionDao questionDao;
    private AnswerDao answerDao;

    public AddAnswerController(QuestionDao questionDao, AnswerDao answerDao) {
        this.questionDao = questionDao;
        this.answerDao = answerDao;
    }

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!UserSessionUtils.isLogin(request.getSession())) {
            return jsonView().addObject("result", Result.fail("Login is required"));
        }

        Answer answer = new Answer(UserSessionUtils.getUserFromSession(request.getSession()).getUserId(), request.getParameter("contents"),
                                Long.parseLong(request.getParameter("questionId")));
        log.debug("answer : {}", answer);

        Answer savedAnswer = answerDao.insert(answer);
        questionDao.updateCountPlusOfAnswer(savedAnswer.getQuestionId());
        return jsonView().addObject("answer", savedAnswer);
    }
}

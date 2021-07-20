package next.Controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.JdbcAnswerDao;
import next.dao.JdbcQuestionDao;
import next.model.Answer;
import next.model.Result;
import next.web.UserSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddAnswerController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(AddAnswerController.class);

    private JdbcAnswerDao jdbcAnswerDao = JdbcAnswerDao.getInstance();
    private JdbcQuestionDao jdbcQuestionDao = JdbcQuestionDao.getInstance();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!UserSessionUtils.isLogin(request.getSession())) {
            return jsonView().addObject("result", Result.fail("Login is required"));
        }

        Answer answer = new Answer(UserSessionUtils.getUserFromSession(request.getSession()).getUserId(), request.getParameter("contents"),
                                Long.parseLong(request.getParameter("questionId")));
        log.debug("answer : {}", answer);

        Answer savedAnswer = jdbcAnswerDao.insert(answer);
        jdbcQuestionDao.updateCountPlusOfAnswer(savedAnswer.getQuestionId());
        return jsonView().addObject("answer", savedAnswer);
    }
}

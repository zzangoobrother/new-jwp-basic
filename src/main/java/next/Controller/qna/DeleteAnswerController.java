package next.Controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.Exception.DataAccessException;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteAnswerController extends AbstractController {
    private AnswerDao answerDao = AnswerDao.getInstance();
    private QuestionDao questionDao = QuestionDao.getInstance();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long answerId = Long.parseLong(request.getParameter("answerId"));

        ModelAndView mav = jsonView();
        try {
            Answer answer = answerDao.findById(answerId);
            questionDao.updateCountMinusOfAnswer(answer.getQuestionId());
            answerDao.delete(answerId);
            mav.addObject("status", Result.ok());
        } catch (DataAccessException e) {
            mav.addObject("result", Result.fail(e.getMessage()));
        }
        return mav;
    }
}

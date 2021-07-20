package next.Controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.Exception.DataAccessException;
import next.dao.JdbcAnswerDao;
import next.dao.JdbcQuestionDao;
import next.model.Answer;
import next.model.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteAnswerController extends AbstractController {
    private JdbcAnswerDao jdbcAnswerDao = JdbcAnswerDao.getInstance();
    private JdbcQuestionDao jdbcQuestionDao = JdbcQuestionDao.getInstance();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long answerId = Long.parseLong(request.getParameter("answerId"));

        ModelAndView mav = jsonView();
        try {
            Answer answer = jdbcAnswerDao.findById(answerId);
            jdbcQuestionDao.updateCountMinusOfAnswer(answer.getQuestionId());
            jdbcAnswerDao.delete(answerId);
            mav.addObject("status", Result.ok());
        } catch (DataAccessException e) {
            mav.addObject("result", Result.fail(e.getMessage()));
        }
        return mav;
    }
}

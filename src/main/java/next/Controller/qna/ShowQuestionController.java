package next.Controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.JdbcAnswerDao;
import next.dao.JdbcQuestionDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowQuestionController extends AbstractController {
    private JdbcQuestionDao jdbcQuestionDao = JdbcQuestionDao.getInstance();
    private JdbcAnswerDao jdbcAnswerDao = JdbcAnswerDao.getInstance();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long questionId = Long.parseLong(request.getParameter("questionId"));

        return jspView("/qna/show.jsp")
                .addObject("question", jdbcQuestionDao.findById(questionId))
                .addObject("answers", jdbcAnswerDao.findAllByQuestionId(questionId));
    }
}

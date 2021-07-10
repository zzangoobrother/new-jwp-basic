package next.Controller.qna;

import core.mvc.*;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowQuestionController extends AbstractController {
    private QuestionDao questionDao = new QuestionDao();
    private AnswerDao answerDao = new AnswerDao();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long questionId = Long.parseLong(request.getParameter("questionId"));

        return jspView("/qna/show.jsp")
                .addObject("question", questionDao.findById(questionId))
                .addObject("answers", answerDao.findAllByQuestionId(questionId));
    }
}

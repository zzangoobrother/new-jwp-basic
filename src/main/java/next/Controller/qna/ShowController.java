package next.Controller.qna;

import core.mvc.Controller;
import core.mvc.JspView;
import core.mvc.View;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowController implements Controller {
    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long questionId = Long.parseLong(request.getParameter("questionId"));
        QuestionDao questionDao = new QuestionDao();
        AnswerDao answerDao = new AnswerDao();

        request.setAttribute("question", questionDao.findById(questionId));
        request.setAttribute("answers", answerDao.findAllByQuestionId(questionId));

        return new JspView("/qna/show.jsp");
    }
}

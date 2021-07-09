package next.Controller.qna;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.mvc.*;
import next.dao.AnswerDao;
import next.model.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class DeleteAnswerController extends AbstractController {
    private AnswerDao answerDao = new AnswerDao();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long answerId = Long.parseLong(request.getParameter("answerId"));

        answerDao.delete(answerId);
        return jsonView().addObject("status", Result.ok());
    }
}

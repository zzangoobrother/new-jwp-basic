package next.Controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.Exception.CannotDeleteException;
import next.model.Result;
import next.service.QnaService;
import next.web.UserSessionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApiDeleteQuestionController extends AbstractController {
    private QnaService qnaService;

    public ApiDeleteQuestionController(QnaService qnaService) {
        this.qnaService = qnaService;
    }

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!UserSessionUtils.isLogin(request.getSession())) {
            return jsonView().addObject("result", Result.fail("Login is required"));
        }

        long questionId = Long.parseLong(request.getParameter("questionId"));
        try {
            qnaService.deleteQuestion(questionId, UserSessionUtils.getUserFromSession(request.getSession()));
            return jsonView().addObject("result" ,Result.ok());
        } catch (CannotDeleteException e) {
            return jsonView().addObject("result", Result.fail(e.getMessage()));
        }
    }
}

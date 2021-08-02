package next.Controller.qna;

import core.mvc.AbstractController;
import core.view.ModelAndView;
import next.Exception.CannotDeleteException;
import next.service.QnaService;
import next.web.UserSessionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteQuestionController extends AbstractController {
    private QnaService qnaService;

    public DeleteQuestionController(QnaService qnaService) {
        this.qnaService = qnaService;
    }

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!UserSessionUtils.isLogin(request.getSession())) {
            return jspView("redirect:/users/loginForm");
        }

        long questionId = Long.parseLong(request.getParameter("questionId"));
        try {
            qnaService.deleteQuestion(questionId, UserSessionUtils.getUserFromSession(request.getSession()));
            return jspView("redirect:/");
        } catch (CannotDeleteException e) {
            return jspView("show.jsp").addObject("question", qnaService.findById(questionId))
                                            .addObject("answers", qnaService.findAllByQuestionId(questionId))
                                            .addObject("errorMessage", e.getMessage());
        }
    }
}

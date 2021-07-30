package next.Controller.qna;

import core.annotation.Controller;
import core.annotation.Inject;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.mvc.ModelAndView;
import core.nmvc.AbstractNewController;
import next.Exception.CannotDeleteException;
import next.Exception.DataAccessException;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Result;
import next.service.QnaService;
import next.web.UserSessionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ApiQuestionController extends AbstractNewController {
    private QnaService qnaService;
    private QuestionDao questionDao;
    private AnswerDao answerDao;

    @Inject
    public ApiQuestionController(QnaService qnaService, QuestionDao questionDao, AnswerDao answerDao) {
        this.qnaService = qnaService;
        this.questionDao = questionDao;
        this.answerDao = answerDao;
    }

    @RequestMapping(value = "/api/qna/addAnswer", method = RequestMethod.POST)
    public ModelAndView addAnswer(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!UserSessionUtils.isLogin(request.getSession())) {
            return jsonView().addObject("result", Result.fail("Login is required"));
        }

        Answer answer = new Answer(UserSessionUtils.getUserFromSession(request.getSession()).getUserId(), request.getParameter("contents"),
                Long.parseLong(request.getParameter("questionId")));

        Answer savedAnswer = answerDao.insert(answer);
        questionDao.updateCountPlusOfAnswer(savedAnswer.getQuestionId());
        return jsonView().addObject("answer", savedAnswer);
    }

    @RequestMapping(value = "/api/qna/deleteAnswer", method = RequestMethod.POST)
    public ModelAndView deleteAnswer(HttpServletRequest request, HttpServletResponse response) throws Exception {
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

    @RequestMapping("/api/qna/list")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return jsonView().addObject("questions", questionDao.findAll());
    }

    @RequestMapping(value = "/api/qna/deleteQuestion", method = RequestMethod.POST)
    public ModelAndView deleteQuestion(HttpServletRequest request, HttpServletResponse respons) throws Exception {
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

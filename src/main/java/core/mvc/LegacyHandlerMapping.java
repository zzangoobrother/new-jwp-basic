package core.mvc;

import core.nmvc.HandlerMapping;
import next.controller.qna.*;
import next.dao.AnswerDao;
import next.dao.JdbcAnswerDao;
import next.dao.JdbcQuestionDao;
import next.dao.QuestionDao;
import next.service.QnaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class LegacyHandlerMapping implements HandlerMapping {
    private static final Logger log = LoggerFactory.getLogger(LegacyHandlerMapping.class);
    private Map<String, Controller> mappings = new HashMap<>();

    void initMapping() {
//        QuestionDao questionDao = new JdbcQuestionDao();
//        AnswerDao answerDao = new JdbcAnswerDao();
//        QnaService qnaService = new QnaService(questionDao, answerDao);

//        mappings.put("/", new HomeController(questionDao));
//        mappings.put("/users/form", new ForwardController("/user/form.jsp"));
//        mappings.put("/users/loginForm", new ForwardController("/user/login.jsp"));
//        mappings.put("/users", new ListUserController());
//        mappings.put("/users/login", new LoginController());
//        mappings.put("/users/profile", new ProfileController());
//        mappings.put("/users/logout", new LogoutController());
//        mappings.put("/users/create", new CreateUserController());
//        mappings.put("/users/updateForm", new UpdateFormUserController());
//        mappings.put("/users/update", new UpdateUserController());
//        mappings.put("/qna/show", new ShowQuestionController(questionDao, answerDao));
//        mappings.put("/qna/form", new CreateFormQuestionController());
//        mappings.put("/qna/create", new CreateQuestionController(questionDao));
//        mappings.put("/qna/updateForm", new UpdateFormQuestionController(questionDao));
//        mappings.put("/qna/update", new UpdateQuestionController(questionDao));
//        mappings.put("/qna/delete", new DeleteQuestionController(qnaService));
//        mappings.put("/api/qna/addAnswer", new AddAnswerController(questionDao, answerDao));
//        mappings.put("/api/qna/deleteAnswer", new DeleteAnswerController(questionDao, answerDao));
//        mappings.put("/api/qna/list", new ApiListQuestionController(questionDao));
//        mappings.put("/api/qna/deleteQuestion", new ApiDeleteQuestionController(qnaService));

        log.info("Initialized Request Mapping!");
    }

    public Controller findController(String url) {
        return mappings.get(url);
    }

    void put(String url, Controller controller) {
        mappings.put(url, controller);
    }

    @Override
    public Controller getHandler(HttpServletRequest request) {
        return mappings.get(request.getRequestURI());
    }
}

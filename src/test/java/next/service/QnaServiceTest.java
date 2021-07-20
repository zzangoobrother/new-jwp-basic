package next.service;

import next.Exception.CannotDeleteException;
import next.dao.MockAnswerDao;
import next.dao.MockQuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.User;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static next.model.UserTest.newUser;

public class QnaServiceTest {
    private QnaService qnaService;
    private MockQuestionDao mockQuestionDao;
    private MockAnswerDao mockAnswerDao;

    @Before
    public void init() {
        mockQuestionDao = new MockQuestionDao();
        mockAnswerDao = new MockAnswerDao();
        qnaService = QnaService.getInstance(mockQuestionDao, mockAnswerDao);
    }

    @Test(expected = CannotDeleteException.class)
    public void deleteQuestion_없는_질문() throws Exception {
        qnaService.deleteQuestion(1L, newUser("userId"));
    }

    @Test(expected = CannotDeleteException.class)
    public void deleteQuestion_삭제할수_있음() throws Exception {
        User user = newUser("userId");
        Question question = new Question(1L, user.getUserId(), "title", "contents", new Date(), 0) {
          public boolean canDelete(User user, List<Answer> answers) throws CannotDeleteException {
              return true;
          }
        };

        qnaService.deleteQuestion(1L, newUser("userId"));
    }
}
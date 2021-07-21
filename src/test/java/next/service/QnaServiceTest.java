package next.service;

import next.Exception.CannotDeleteException;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import java.util.List;

import static next.model.UserTest.newUser;

@RunWith(MockitoJUnitRunner.class)
public class QnaServiceTest {
    private QnaService qnaService;

    @Mock
    private QuestionDao questionDao;

    @Mock
    private AnswerDao answerDao;

    @Before
    public void init() {
        qnaService = QnaService.getInstance(questionDao, answerDao);
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
          };
        };

        qnaService.deleteQuestion(1L, newUser("userId"));
    }

    @Test(expected = CannotDeleteException.class)
    public void deleteQuestion_삭제할수_없음() throws Exception {
        User user = newUser("userId");
        Question question = new Question(1L, user.getUserId(), "title", "contents", new Date(), 0) {
            public boolean canDelete(User user, List<Answer> answers) throws CannotDeleteException {
                throw new CannotDeleteException("삭제할 수 없음");
            };
        };

        qnaService.deleteQuestion(1L, newUser("userId"));
    }

}
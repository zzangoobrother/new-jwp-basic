package next.model;

import next.Exception.CannotDeleteException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static next.model.AnswerTest.newAnswer;
import static next.model.UserTest.newUser;
import static org.junit.Assert.*;

public class QuestionTest {
    public static Question newQuestion(String writer) {
        return new Question(1L, writer, "title", "contents", new Date(), 0);
    }

    public static Question newQuestion(long questionId, String writer) {
        return new Question(questionId, writer, "title", "contents", new Date(), 0);
    }

    @Test(expected = CannotDeleteException.class)
    public void canDelete_글쓴이_다르다() throws Exception {
        User user = newUser("choi");
        Question question = newQuestion("sanjigi");
        question.canDelete(user, new ArrayList<Answer>());
    }

    @Test
    public void canDelete_글쓴이_같음_답변_없음() throws Exception {
        User user = newUser("choi");
        Question question = newQuestion("choi");
        assertTrue(question.canDelete(user, new ArrayList<Answer>()));
    }

    @Test
    public void canDelete_같은_사용자_답변() throws Exception {
        String userId = "choi";
        User user = newUser(userId);
        Question question = newQuestion(userId);
        List<Answer> answers = Arrays.asList(newAnswer(userId));
        assertTrue(question.canDelete(user, answers));
    }

    @Test(expected = CannotDeleteException.class)
    public void canDelete_다른_사용자_답변() throws Exception {
        String userId = "choi";
        List<Answer> answers = Arrays.asList(newAnswer(userId), newAnswer("csk"));
        newQuestion(userId).canDelete(newUser(userId), answers);
    }
}
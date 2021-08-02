package next.dao;

import com.google.common.collect.Maps;
import next.model.Answer;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MockAnswerDao implements AnswerDao {
    private Map<Long, Answer> answers = Maps.newHashMap();

    @Override
    public Answer insert(Answer answer) {
        return answers.put(answer.getAnswerId(), answer);
    }

    @Override
    public Answer findById(long answerId) {
        return answers.get(answerId);
    }

    @Override
    public List<Answer> findAllByQuestionId(long questionId) {
        return answers.values().stream()
                .filter(a -> a.getQuestionId() == questionId)
                .collect(Collectors.toList());
    }

    @Override
    public void update(Answer answer) {

    }

    @Override
    public void delete(long answerId) {
        answers.remove(answerId);
    }
}

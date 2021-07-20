package next.dao;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import next.model.Question;

import java.util.Collection;
import java.util.Map;

public class MockQuestionDao implements QuestionDao {
    private Map<Long, Question> questions = Maps.newHashMap();

    @Override
    public Question insert(Question question) {
        return questions.put(question.getQuestionId(), question);
    }

    @Override
    public Collection<Question> findAll() {
        return Lists.newArrayList(questions.values());
    }

    @Override
    public Question findById(long questionId) {
        return questions.get(questionId);
    }

    @Override
    public void updateCountPlusOfAnswer(long questionId) {

    }

    @Override
    public void updateCountMinusOfAnswer(long questionId) {

    }

    @Override
    public void update(Question question) {
        findById(question.getQuestionId()).update(question);
    }

    @Override
    public void delete(long questionId) {
        questions.remove(questionId);
    }
}

package next.dao;

import next.model.Question;

import java.util.Collection;

public interface QuestionDao {

    Question insert(Question question);

    Collection<Question> findAll();

    Question findById(long questionId);

    void updateCountPlusOfAnswer(long questionId);

    void updateCountMinusOfAnswer(long questionId);

    void update(Question question);

    void delete(long questionId);
}

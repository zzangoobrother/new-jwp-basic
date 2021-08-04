package ormNext.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ormNext.model.Answer;
import ormNext.model.Question;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByQuestion(Question question);
}

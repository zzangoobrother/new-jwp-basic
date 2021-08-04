package ormNext.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ormNext.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}

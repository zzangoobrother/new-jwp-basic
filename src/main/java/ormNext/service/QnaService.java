package ormNext.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ormNext.model.Question;
import ormNext.repository.AnswerRepository;
import ormNext.repository.QuestionRepository;

import javax.inject.Inject;
import java.util.List;

@Service
@Transactional
public class QnaService {
    private QuestionRepository questionRepository;
    private AnswerRepository answerRepository;

    @Inject
    public QnaService(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public List<Question> findQuestions() {
        return questionRepository.findAll();
    }

}

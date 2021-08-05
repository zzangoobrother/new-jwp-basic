package ormNext.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ormNext.CannotOperateException;
import ormNext.model.Answer;
import ormNext.model.Question;
import ormNext.model.User;
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

    public Question findById(Long questionId) {
        return questionRepository.findOne(questionId);
    }

    public List<Answer> findAllByQuestionId(Long questionId) {
        return answerRepository.findByQuestion(findById(questionId));
    }

    public void deleteQuestion(Long questionId, User user) throws CannotOperateException {
        Question question = findById(questionId);
        if (question == null) {
            throw new CannotOperateException("존재하지 않는 질문입니다.");
        }

        if (question.canDelete(user)) {
            questionRepository.delete(question);
        }
    }

    public void create(Question question, User user) {
        questionRepository.save(question.writeBy(user));
    }

    public void update(Question editQuestion, User user) {
        Question question = questionRepository.findOne(editQuestion.getQuestionId());
        if (!question.isSameWriter(user)) {
            throw new IllegalStateException("다른 사용자가 쓴 글을 수정할 수 없습니다.");
        }
        question.update(editQuestion);
    }

    public Answer addAnswer(User writer, Long questionId, String contents) {
        Question question = findById(questionId);
        Answer newAnswer = new Answer(writer, contents, question);
        question.addAnswer();
        return answerRepository.save(newAnswer);
    }

    public void deleteAnswer(Long answerId, User loginUser) {
        Answer answer = answerRepository.findOne(answerId);
        if (!answer.isSameWriter(loginUser)) {
            throw new IllegalStateException("다른 사용자가 쓴 글을 삭제할 수 없습니다.");
        }
        answerRepository.delete(answer);
    }
}

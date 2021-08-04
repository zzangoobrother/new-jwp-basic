package ormNext.model;

import com.google.common.collect.Lists;
import org.hibernate.validator.constraints.NotBlank;
import ormNext.CannotOperateException;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long questionId;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @NotBlank
    @Size(min = 4, max = 50)
    @Column(length = 50, nullable = false)
    private String title;

    @NotBlank
    @Size(min = 4, max = 5000)
    @Column(length = 5000, nullable = false)
    private String contents;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdDate;

    private int countofComment;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    @OrderBy("answerId ASC")
    private List<Answer> answers;

    public Question() {}

    public Question(User writer, String title, String contents, List<Answer> answers) {
        this(0L, writer, title, contents, new Date(), 0, answers);
    }

    public Question(Long questionId, User writer, String title, String contents, Date createdDate, int countofComment, List<Answer> answers) {
        this.questionId = questionId;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDate = createdDate;
        this.countofComment = countofComment;
        this.answers = answers;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getCountofComment() {
        return countofComment;
    }

    public void setCountofComment(int countofComment) {
        this.countofComment = countofComment;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public boolean isSameWriter(User user) {
        return user.isSameUser(this.writer);
    }

    public Question writeBy(User user) {
        return new Question(user, title, contents, Lists.newArrayList());
    }

    public void update(Question newQuestion) {
        this.title = newQuestion.title;
        this.contents = newQuestion.contents;
    }

    public void addAnswer() {
        this.countofComment += 1;
    }

    public boolean canDelete(User loginUser) throws CannotOperateException {
        if (!loginUser.isSameUser(this.writer)) {
            throw new CannotOperateException("다른 사용자가 쓴 글을 삭제할 수 없습니다.");
        }

        for (Answer answer : this.answers) {
            if (!answer.canDelete(loginUser)) {
                throw new CannotOperateException("다른 사용자가 추가한 댓글이 존재해 삭제할 수 없습니다.");
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
                ", writer=" + writer +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", createdDate=" + createdDate +
                ", countofComment=" + countofComment +
                ", answers=" + answers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        if (countofComment != question.countofComment) return false;
        if (questionId != null ? !questionId.equals(question.questionId) : question.questionId != null) return false;
        if (writer != null ? !writer.equals(question.writer) : question.writer != null) return false;
        if (title != null ? !title.equals(question.title) : question.title != null) return false;
        if (contents != null ? !contents.equals(question.contents) : question.contents != null) return false;
        if (createdDate != null ? !createdDate.equals(question.createdDate) : question.createdDate != null)
            return false;
        return answers != null ? answers.equals(question.answers) : question.answers == null;
    }

    @Override
    public int hashCode() {
        int result = questionId != null ? questionId.hashCode() : 0;
        result = 31 * result + (writer != null ? writer.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (contents != null ? contents.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + countofComment;
        result = 31 * result + (answers != null ? answers.hashCode() : 0);
        return result;
    }
}

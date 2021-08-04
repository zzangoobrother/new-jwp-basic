package ormNext.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long answerId;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @NotBlank
    @Size(min = 4, max = 5000)
    @Column(length = 5000, nullable = false)
    private String contents;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createDate;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_question"))
    private Question question;

    public Answer() {}

    public Answer(User writer, String contents, Question question) {
        this(0L, writer, contents, new Date(), question);
    }

    public Answer(Long answerId, User writer, String contents, Date createDate, Question question) {
        this.answerId = answerId;
        this.writer = writer;
        this.contents = contents;
        this.createDate = createDate;
        this.question = question;
    }

    public long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(long answerId) {
        this.answerId = answerId;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public boolean canDelete(User user) {
        return user.isSameUser(this.writer);
    }

    public boolean isSameWriter(User user) {
        return user.isSameUser(this.writer);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        if (answerId != answer.answerId) return false;
        if (writer != null ? !writer.equals(answer.writer) : answer.writer != null) return false;
        if (contents != null ? !contents.equals(answer.contents) : answer.contents != null) return false;
        if (createDate != null ? !createDate.equals(answer.createDate) : answer.createDate != null) return false;
        return question != null ? question.equals(answer.question) : answer.question == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (answerId ^ (answerId >>> 32));
        result = 31 * result + (writer != null ? writer.hashCode() : 0);
        result = 31 * result + (contents != null ? contents.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (question != null ? question.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answerId=" + answerId +
                ", writer=" + writer +
                ", contents='" + contents + '\'' +
                ", createDate=" + createDate +
                ", question=" + question +
                '}';
    }
}

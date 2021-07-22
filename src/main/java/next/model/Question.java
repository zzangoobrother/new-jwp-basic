package next.model;

import next.Exception.CannotDeleteException;

import java.util.Date;
import java.util.List;

public class Question {
    private Long questionId;
    private String writer;
    private String title;
    private String contents;
    private Date createdDate;
    private int countOfComment;

    public Question(String writer, String title, String contents) {
        this(0, writer, title, contents, new Date(), 0);
    }

    public Question(long questionId, String writer, String title,
                    String contents, Date createdDate,
                    int countOfComment) {
        this.questionId = questionId;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDate = createdDate;
        this.countOfComment = countOfComment;
    }

    public long getQuestionId() {
        return questionId;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public long getTimeFromCreateDate() {
        return this.createdDate.getTime();
    }

    public int getCountOfComment() {
        return countOfComment;
    }

    public boolean isSameUser(User user) {
        return user.isSameUser(this.writer);
    }

    public void update(Question newQuestion) {
        this.title = newQuestion.title;
        this.contents = newQuestion.contents;
    }

    public boolean canDelete(User user, List<Answer> answers) throws CannotDeleteException {
        if (!user.isSameUser(this.writer)) {
            throw new CannotDeleteException("다른 사용자가 쓴 글을 삭제할 수 없습니다.");
        }

        for (Answer answer : answers) {
            if (!answer.canDelete(user)) {
                throw new CannotDeleteException("다른 사용자가 추가한 댓글이 존재해 삭제할 수 없습니다.");
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", createdDate=" + createdDate +
                ", countOfComment=" + countOfComment +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        if (countOfComment != question.countOfComment) return false;
        if (questionId != null ? !questionId.equals(question.questionId) : question.questionId != null) return false;
        if (writer != null ? !writer.equals(question.writer) : question.writer != null) return false;
        if (title != null ? !title.equals(question.title) : question.title != null) return false;
        if (contents != null ? !contents.equals(question.contents) : question.contents != null) return false;
        return createdDate != null ? createdDate.equals(question.createdDate) : question.createdDate == null;
    }

    @Override
    public int hashCode() {
        int result = questionId != null ? questionId.hashCode() : 0;
        result = 31 * result + (writer != null ? writer.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (contents != null ? contents.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + countOfComment;
        return result;
    }
}

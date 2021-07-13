package next.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.KeyHolder;
import core.jdbc.PreparedStatementCreator;
import core.jdbc.RowMapper;
import next.model.Question;
import next.model.User;

import java.sql.*;
import java.util.Collection;

public class QuestionDao {
    private JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();
    private static QuestionDao questionDao = new QuestionDao();

    private QuestionDao() {
    }

    public static QuestionDao getInstance() {
        return questionDao;
    }

    public Question insert(Question question) {
        String sql = "INSERT INTO QUESTIONS (questionId, writer, title, contents, createdDate, countOfAnswer) VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setLong(1, question.getQuestionId());
                pstmt.setString(2, question.getWriter());
                pstmt.setString(3, question.getTitle());
                pstmt.setString(4, question.getContents());
                pstmt.setTimestamp(5, new Timestamp(question.getTimeFromCreateDate()));
                pstmt.setLong(6, question.getCountOfComment());
                return pstmt;
            }
        };

        KeyHolder keyHolder = new KeyHolder();
        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        return findById(keyHolder.getId());
    }

    public Collection<Question> findAll() {
        String sql = "SELECT * FROM QUESTIONS ORDER BY questionId DESC";

        RowMapper<Question> rowMapper = new RowMapper<Question>() {
            @Override
            public Question mapRow(ResultSet rs) throws SQLException {
                return new Question(rs.getLong("questionId"),
                        rs.getString("writer"),
                        rs.getString("title"),
                        rs.getString("contents"),
                        rs.getTimestamp("createdDate"),
                        rs.getInt("countOfAnswer"));
            }
        };

        return jdbcTemplate.query(sql, rowMapper);
    }

    public Question findById(long questionId) {
        String sql = "SELECT * FROM QUESTIONS WHERE questionId = ?";

        RowMapper<Question> rowMapper = new RowMapper<Question>() {
            @Override
            public Question mapRow(ResultSet rs) throws SQLException {
                return new Question(rs.getLong("questionId"),
                        rs.getString("writer"),
                        rs.getString("title"),
                        rs.getString("contents"),
                        rs.getTimestamp("createdDate"),
                        rs.getInt("countOfAnswer"));
            }
        };

        return jdbcTemplate.queryForObject(sql, rowMapper, questionId);
    }

    public void updateCountOfAnswer(long questionId) {
        String sql = "UPDATE QUESTIONS SET countOfAnswer = countOfAnswer + 1 WHERE questionId = ?";
        jdbcTemplate.update(sql, questionId);
    }

    public void update(Question question) {
        String sql = "UPDATE QUESTIONS SET title = ?, contents = ? WHERE questionId = ?";
        jdbcTemplate.update(sql, question.getTitle(), question.getContents(), question.getQuestionId());
    }

    public void delete(long questionId) {
        String sql = "DELETE FROM QUESTIONS WHERE questionId = ?";
        jdbcTemplate.update(sql, questionId);
    }
}

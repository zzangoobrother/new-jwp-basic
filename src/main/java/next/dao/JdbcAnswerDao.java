package next.dao;

import core.annotation.Inject;
import core.annotation.Repository;
import core.jdbc.JdbcTemplate;
import core.jdbc.KeyHolder;
import core.jdbc.PreparedStatementCreator;
import core.jdbc.RowMapper;
import next.model.Answer;
import next.model.Question;

import java.sql.*;
import java.util.List;

@Repository
public class JdbcAnswerDao implements AnswerDao {
    private JdbcTemplate jdbcTemplate;

    @Inject
    public JdbcAnswerDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Answer insert(Answer answer) {
        String sql = "INSERT INTO ANSWERS (writer, contents, createdDate, questionId) VALUES (?, ?, ?, ?)";
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1, answer.getWriter());
                pstmt.setString(2, answer.getContents());
                pstmt.setTimestamp(3, new Timestamp(answer.getTimeFromCreateDate()));
                pstmt.setLong(4, answer.getQuestionId());
                return pstmt;
            }
        };

        KeyHolder keyHolder = new KeyHolder();
        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        return findById(keyHolder.getId());
    }

    @Override
    public Answer findById(long answerId) {
        String sql = "SELECT * FROM ANSWERS WHERE answerId = ?";

        RowMapper<Answer> rowMapper = new RowMapper<Answer>() {
            @Override
            public Answer mapRow(ResultSet rs) throws SQLException {
                return new Answer(rs.getLong("answerId"),
                        rs.getString("writer"),
                        rs.getString("contents"),
                        rs.getTimestamp("createdDate"),
                        rs.getLong("questionId"));
            }
        };

        return jdbcTemplate.queryForObject(sql, rowMapper, answerId);
    }

    @Override
    public List<Answer> findAllByQuestionId(long questionId) {
        String sql = "SELECT * FROM ANSWERS WHERE questionId = ? ORDER BY answerId DESC";

        RowMapper<Answer> rowMapper = new RowMapper<Answer>() {
            @Override
            public Answer mapRow(ResultSet rs) throws SQLException {
                return new Answer(rs.getLong("answerId"),
                        rs.getString("writer"),
                        rs.getString("contents"),
                        rs.getTimestamp("createdDate"),
                        rs.getLong("questionId"));
            }
        };

        return jdbcTemplate.query(sql, rowMapper, questionId);
    }

    @Override
    public void delete(long answerId) {
        String sql = "DELETE FROM ANSWERS WHERE answerId = ?";
        jdbcTemplate.update(sql, answerId);
    }
}

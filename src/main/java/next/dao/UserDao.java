package next.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;
import next.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class UserDao {
    private JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();
    private static UserDao userDao = new UserDao();

    private UserDao() {
    }

    public static UserDao getInstance() {
        return userDao;
    }

    public void insert(User user) {
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUserId(), user.getPassword(),
                user.getName(), user.getEmail());
    }

    public User findByUserId(String userId) {
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
        return (User) jdbcTemplate.queryForObject(sql, (ResultSet rs) -> {
            return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                    rs.getString("email"));
        }, userId);
    }

    public Collection<User> findAll() {
        String sql = "SELECT * FROM USERS";
        return jdbcTemplate.query(sql, (ResultSet rs) -> {
            return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                    rs.getString("email"));
        });
    }

    public void update(User user) {
        String sql = "UPDATE USERS SET password = ?, name = ?, email = ? WHERE userId = ?";
        jdbcTemplate.update(sql, user.getPassword(), user.getName(),
                user.getEmail(), user.getUserId());
    }
}

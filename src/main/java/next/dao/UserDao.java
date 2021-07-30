package next.dao;

import core.annotation.Inject;
import core.annotation.Repository;
import core.jdbc.JdbcTemplate;
import next.model.User;

import java.sql.ResultSet;
import java.util.Collection;

@Repository
public class UserDao {
    private JdbcTemplate jdbcTemplate;

    @Inject
    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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

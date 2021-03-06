package core.jdbc;

import core.annotation.Component;
import core.annotation.Inject;
import next.Exception.DataAccessException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTemplate {
    private DataSource dataSource;

    @Inject
    public JdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void update(String sql, PreparedStatementSetter pss) throws DataAccessException {
        try(Connection con = dataSource.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {
            pss.setValues(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public void update(String sql, Object... parameters) throws DataAccessException {
        update(sql, createPreparedStatementSetter(parameters));
    }

    public void update(PreparedStatementCreator psc, KeyHolder key) {
        try(Connection con = dataSource.getConnection();
            PreparedStatement pstmt = psc.createPreparedStatement(con)) {
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                key.setId(rs.getLong(1));
            }
            rs.close();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public <T> List<T> query(String sql, RowMapper<T> rowMapper, PreparedStatementSetter pss) throws DataAccessException {
        ResultSet rs = null;

        List<T> users = new ArrayList<>();
        try(Connection con = dataSource.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pss.setValues(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                users.add(rowMapper.mapRow(rs));
            }
            return users;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                throw new DataAccessException(e);
            }
        }
    }

    public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... parameters) throws DataAccessException {
       return query(sql, rowMapper, createPreparedStatementSetter(parameters));
    }

    public <T> T queryForObject(String sql, RowMapper<T> rowMapper, PreparedStatementSetter pss) throws DataAccessException {
        List<T> users = query(sql, rowMapper, pss);
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    public <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object... parameters) throws DataAccessException {
        return queryForObject(sql, rowMapper, createPreparedStatementSetter(parameters));
    }

    private PreparedStatementSetter createPreparedStatementSetter(Object... parameters) {
        return new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement pstmt) throws SQLException {
                for (int i = 0; i < parameters.length; i++) {
                    pstmt.setObject(i+1, parameters[i]);
                }
            }
        };
    }
}

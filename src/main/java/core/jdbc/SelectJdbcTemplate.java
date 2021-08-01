package core.jdbc;

import next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class SelectJdbcTemplate {
//    public List<User> query(String sql) throws SQLException {
//        Connection con = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//
//        List<User> users = new ArrayList<>();
//        try {
//            con = ConnectionManager.getConnection();
//            pstmt = con.prepareStatement(sql);
//            setValues(pstmt);
//            rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//                users.add((User) mapRow(rs));
//            }
//            return users;
//        } finally {
//            if (rs != null) {
//                rs.close();
//            }
//
//            if (pstmt != null) {
//                pstmt.close();
//            }
//
//            if (con != null) {
//                con.close();
//            }
//        }
//    }

//    public Object queryForObject(String sql) throws SQLException {
//        List<User> users = query(sql);
//        if (users.isEmpty()) {
//            return null;
//        }
//        return users.get(0);
//    }

    abstract public void setValues(PreparedStatement pstmt) throws SQLException;

    abstract public Object mapRow(ResultSet rs) throws SQLException;
}

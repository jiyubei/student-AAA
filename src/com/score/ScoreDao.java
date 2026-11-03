package com.score;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 成绩 DAO 数据访问层（JDBC + MySQL）
 * 负责成绩的增、删、改、查
 */
public class ScoreDao implements Serializable {

    private static final long serialVersionUID = 1L;

    private static String url;
    private static String username;
    private static String password;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 请根据本机 MySQL 环境修改
            url = "jdbc:mysql://localhost:3306/score_system"
                    + "?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai";
            username = "root";
            password = "";
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("加载MySQL驱动失败: " + e.getMessage());
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * 查询全部成绩
     */
    public List<Score> findAll() {
        List<Score> list = new ArrayList<>();
        String sql = "SELECT id, sno, student_name, course_name, score, create_time FROM t_score ORDER BY id DESC";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Score s = new Score();
                s.setId(rs.getInt("id"));
                s.setSno(rs.getString("sno"));
                s.setStudentName(rs.getString("student_name"));
                s.setCourseName(rs.getString("course_name"));
                s.setScore(rs.getDouble("score"));
                s.setCreateTime(rs.getTimestamp("create_time"));
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 根据ID查询成绩
     */
    public Score findById(int id) {
        String sql = "SELECT id, sno, student_name, course_name, score, create_time FROM t_score WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Score s = new Score();
                    s.setId(rs.getInt("id"));
                    s.setSno(rs.getString("sno"));
                    s.setStudentName(rs.getString("student_name"));
                    s.setCourseName(rs.getString("course_name"));
                    s.setScore(rs.getDouble("score"));
                    s.setCreateTime(rs.getTimestamp("create_time"));
                    return s;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 新增成绩
     */
    public boolean add(Score score) {
        String sql = "INSERT INTO t_score (sno, student_name, course_name, score) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, score.getSno());
            ps.setString(2, score.getStudentName());
            ps.setString(3, score.getCourseName());
            ps.setDouble(4, score.getScore());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新成绩
     */
    public boolean update(Score score) {
        String sql = "UPDATE t_score SET sno = ?, student_name = ?, course_name = ?, score = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, score.getSno());
            ps.setString(2, score.getStudentName());
            ps.setString(3, score.getCourseName());
            ps.setDouble(4, score.getScore());
            ps.setInt(5, score.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除成绩
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM t_score WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

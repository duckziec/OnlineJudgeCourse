package dao;

import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends BaseDAO<User> implements RowMapper<User> {

    private static UserDAO userDAO;

    private UserDAO() {
    }

    public static UserDAO getInstance() {
        if (userDAO == null) {
            userDAO = new UserDAO();
        }
        return userDAO;
    }

    @Override
    public User mapRow(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setFullName(rs.getString("full_name"));
        user.setUserName(rs.getString("user_name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setCreateAt(rs.getTimestamp("create_at").toLocalDateTime());
        // check for reset token and expiry
        String resetToken = rs.getString("reset_token");
        user.setResetToken(resetToken != null ? resetToken : "");
        String tokenExpiryStr = rs.getString("token_expiry");
        user.setTokenExpiry(tokenExpiryStr != null ? rs.getTimestamp("token_expiry").toLocalDateTime() : LocalDateTime.now());
        return user;
    }


    // Thêm user
    public boolean addUser(User user) {
        String sql = "INSERT INTO users (full_name, user_name, email, password) VALUES (?, ?, ?, ?)";
        return executeUpdate(sql, user.getFullName(), user.getUserName(), user.getEmail(), user.getPassword());
    }

    // Cập nhật user
    public boolean updateUserById(User user) {
        String sql = "UPDATE users SET full_name = ?, user_name = ?, email = ?, password = ? WHERE user_id = ?";
        return executeUpdate(sql, user.getFullName(), user.getUserName(), user.getEmail(), user.getPassword(), user.getUserId());
    }

    // cập nhật user theo username
    public boolean updateUserByUsername(User user) {
        String sql = "UPDATE users SET full_name = ?, user_name = ?, email = ?, password = ? WHERE user_name = ?";
        return executeUpdate(sql, user.getFullName(), user.getUserName(), user.getEmail(), user.getPassword(), user.getUserName());
    }

    public boolean updateUserPassword(int userId, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE user_id = ?";
        return executeUpdate(sql, newPassword, userId);
    }

    // Xoá user
    public boolean deleteUser(int id) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        return executeUpdate(sql, id);
    }

    // Tìm user theo ID
    public User findById(int id) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        List<User> list = executeQuery(sql, this, id);
        return list.isEmpty() ? null : list.get(0);
    }

    // Tìm user theo username
    public User findByUserName(String userName) {
        String sql = "SELECT * FROM users WHERE user_name = ?";
        List<User> list = executeQuery(sql, this, userName);
        return list.isEmpty() ? null : list.get(0);
    }

    // tìm user theo email
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        List<User> list = executeQuery(sql, this, email);
        return list.isEmpty() ? null : list.get(0);
    }

    //tim user theo user hoặc email
    public User findByUserOrEmail(String identifier) {
        String sql = "SELECT * FROM users WHERE user_name = ? OR email = ?";
        List<User> list = executeQuery(sql, this, identifier, identifier);
        return list.isEmpty() ? null : list.get(0);
    }

    // Lấy toàn bộ user
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return executeQuery(sql, this);
    }

    // check trùng email hoặc username
    public boolean existByUserNameOrEmail(String userName, String email) {
        String sql = "SELECT * FROM users WHERE user_name = ? OR email = ?";
        List<User> list = executeQuery(sql, this, userName, email);
        return !list.isEmpty();
    }

    // lấy tất cả user của khoá học đó
    public List<User> getUsersByCourse(int courseId) {
        List<User> list = new ArrayList<>();
        String sql = """
                    SELECT u.* FROM users u
                    JOIN enrollments e ON u.user_id = e.user_id
                    WHERE e.course_id = ?
                """;
        return executeQuery(sql, this, courseId);
    }


    public boolean updateResetToken(int userId, String token, LocalDateTime expiryTime) {
        String sql = "UPDATE users SET reset_token = ?, token_expiry = ? WHERE user_id = ?";
        return executeUpdate(sql, token, expiryTime, userId);
    }

    public User getUserByResetToken(String token) {
        String sql = "SELECT * FROM users WHERE reset_token = ?";
        List<User> list = executeQuery(sql, rs -> {
            User user = new User();
            user.setUserId(rs.getInt("user_id"));
            user.setFullName(rs.getString("full_name"));
            user.setUserName(rs.getString("user_name"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setCreateAt(rs.getTimestamp("create_at").toLocalDateTime());
            user.setResetToken(rs.getString("reset_token"));
            user.setTokenExpiry(rs.getTimestamp("token_expiry") != null ? rs.getTimestamp("token_expiry").toLocalDateTime() : null);
            return user;
        }, token);
        return list.isEmpty() ? null : list.get(0);
    }

    public boolean clearResetToken(int userId) {
        String sql = "UPDATE users SET reset_token = NULL, token_expiry = NULL WHERE user_id = ?";
        return executeUpdate(sql, userId);
    }

}

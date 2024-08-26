package log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {   
    private static final String DB_URL = "jdbc:mysql://113.198.238.95:3306/restaurant";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";

    // 회원가입 기능
    public boolean registerUser(String userId, String password, String name, String phone) {
        String query = "INSERT INTO user (user_id, user_pwd, user_name, user_phone, user_temp) VALUES (?, ?, ?, ?, 0)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, userId);
            stmt.setString(2, password);
            stmt.setString(3, name);
            stmt.setString(4, phone);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 로그인 기능
    public boolean loginUser(String userId, String password) {
        String query = "SELECT * FROM user WHERE user_id = ? AND user_pwd = ? AND user_temp = 0";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, userId);
            stmt.setString(2, password);

            return stmt.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 비회원 로그인 기능
    public boolean guestLogin(String name, String phone) {
        String selectQuery = "SELECT * FROM user WHERE user_name = ? AND user_phone = ? AND user_temp = 1";
        String insertQuery = "INSERT INTO user (user_id, user_pwd, user_name, user_phone, user_temp) VALUES (?, ?, ?, ?, 1)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {

            selectStmt.setString(1, name);
            selectStmt.setString(2, phone);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                // 비회원 계정이 이미 존재하면 로그인 성공
                return true;
            } else {
                // 비회원 계정이 없으면 새로 생성
                try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                    insertStmt.setString(1, phone); // 비회원 아이디는 전화번호로 대체
                    insertStmt.setString(2, phone); // 비회원 비밀번호는 전화번호로 대체
                    insertStmt.setString(3, name);
                    insertStmt.setString(4, phone);
                    int rowsAffected = insertStmt.executeUpdate();
                    return rowsAffected > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 관리자 로그인 기능
    public boolean loginAdmin(String userId, String password) {
        String query = "SELECT * FROM manager WHERE manager_id = ? AND manager_pwd = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, userId);
            stmt.setString(2, password);

            return stmt.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 아이디 찾기 기능
    public String findUserId(String name, String phone) {
        String query = "SELECT user_id FROM user WHERE user_name = ? AND user_phone = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setString(2, phone);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("user_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 비밀번호 찾기 기능
    public String findUserPassword(String userId, String phone) {
        String query = "SELECT user_pwd FROM user WHERE user_id = ? AND user_phone = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, userId);
            stmt.setString(2, phone);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("user_pwd");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 전화번호 중복 확인 기능
    public boolean isPhoneNumberDuplicated(String phoneNumber) {
        String query = "SELECT COUNT(*) FROM user WHERE user_phone = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, phoneNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

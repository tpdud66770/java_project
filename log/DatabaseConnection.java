package log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://113.198.238.95/restaurant"; // 데이터베이스 이름
    private static final String USER = "root"; // MySQL 사용자 이름
    private static final String PASSWORD = "1234"; // MySQL 비밀번호

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

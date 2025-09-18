package vn.edu.hcmute.fit.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPool {
    // In biến môi trường để kiểm tra
    static {
        System.out.println("DB_HOST: " + System.getenv("DB_HOST"));
        System.out.println("DB_NAME: " + System.getenv("DB_NAME"));
        System.out.println("DB_USER: " + System.getenv("DB_USER"));
        System.out.println("DB_PASS: " + System.getenv("DB_PASS"));
    }

    private static final String URL = "jdbc:postgresql://" +
        System.getenv("DB_HOST") + "/" +
        System.getenv("DB_NAME") +
        "?sslmode=require&options=endpoint%3Dep-dark-river-a1eh2bfx";

    private static final String USER = System.getenv("DB_USER");
    private static final String PASSWORD = System.getenv("DB_PASS");

    private static final ConnectionPool instance = new ConnectionPool();

    private ConnectionPool() {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL JDBC Driver loaded successfully.");
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC Driver not found: " + e.getMessage());
        }
    }

    public static ConnectionPool getInstance() {
        return instance;
    }

    public Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Kết nối thành công tới Neon PostgreSQL");
            return conn;
        } catch (SQLException e) {
            System.err.println("❌ Lỗi kết nối JDBC: " + e.getMessage());
            throw e;
        }
    }
}

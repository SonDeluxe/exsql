package vn.edu.hcmute.fit.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPool {
    private static final String URL = "jdbc:postgresql://" +
        System.getenv("DB_HOST") + "/" +
        System.getenv("DB_NAME") +
        "?sslmode=require&options=endpoint%3Dep-dark-river-a1eh2bfx";

    
    private static final String USER = System.getenv("DB_USER");
    private static final String PASSWORD = System.getenv("DB_PASS");

    // Khởi tạo instance duy nhất
    private static final ConnectionPool instance = new ConnectionPool();

    private ConnectionPool() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC Driver not found: " + e.getMessage());
        }
    }

    public static ConnectionPool getInstance() {
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

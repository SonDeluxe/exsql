package vn.edu.hcmute.fit.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPool {
    // ✅ Chuỗi JDBC đã thêm sslmode=require để bật SSL
    private static final String URL = "jdbc:postgresql://dpg-d35v54hr0fns73bgc2n0-a:5432/exsql?sslmode=require";
    private static final String USER = "sonhcmute";
    private static final String PASSWORD = "avjtqoPEtBoknVpwcJszLXGX8cIh35sf";

    private static final ConnectionPool instance = new ConnectionPool();

    private ConnectionPool() {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("✅ PostgreSQL JDBC Driver loaded.");
            System.out.println("🔗 Chuỗi JDBC: " + URL);
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Driver not found: " + e.getMessage());
        }
    }

    public static ConnectionPool getInstance() {
        return instance;
    }

    public Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Kết nối thành công tới Render PostgreSQL");
            return conn;
        } catch (SQLException e) {
            System.err.println("❌ Lỗi kết nối JDBC: " + e.getMessage());
            throw e;
        }
    }
}

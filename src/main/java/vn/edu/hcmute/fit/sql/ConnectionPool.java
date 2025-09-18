package vn.edu.hcmute.fit.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPool {
    // Thông tin kết nối ghi trực tiếp
    private static final String URL = "jdbc:postgresql://ep-dark-river-a1eh2bfx-pooler.ap-southeast-1.aws.neon.tech/neondb?sslmode=require&options=endpoint%3Dep-dark-river-a1eh2bfx";
    private static final String USER = "neondb_owner";
    private static final String PASSWORD = "ngp_bZXT8Ml2yzLAep";

    private static final ConnectionPool instance = new ConnectionPool();

    private ConnectionPool() {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("✅ PostgreSQL JDBC Driver loaded.");
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
            System.out.println("✅ Kết nối thành công tới Neon PostgreSQL");
            return conn;
        } catch (SQLException e) {
            System.err.println("❌ Lỗi kết nối JDBC: " + e.getMessage());
            throw e;
        }
    }
}

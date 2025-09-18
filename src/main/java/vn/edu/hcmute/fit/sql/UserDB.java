package vn.edu.hcmute.fit.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDB {

    public static int insert(User user) {
        if (user == null) return 0;

        String query = "INSERT INTO Users (Email, FirstName, LastName) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, user.getEmail());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());

            return ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Insert failed: " + e.getMessage());
            return 0;
        }
    }

    public static int update(User user) {
        if (user == null) return 0;

        String query = "UPDATE Users SET FirstName = ?, LastName = ? WHERE Email = ?";
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());

            return ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Update failed: " + e.getMessage());
            return 0;
        }
    }

    public static int delete(User user) {
        if (user == null || user.getEmail() == null) return 0;

        String query = "DELETE FROM Users WHERE Email = ?";
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, user.getEmail());
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Delete failed: " + e.getMessage());
            return 0;
        }
    }

    public static boolean emailExists(String email) {
        if (email == null || email.isEmpty()) return false;

        String query = "SELECT Email FROM Users WHERE Email = ?";
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Email check failed: " + e.getMessage());
            return false;
        }
    }

    public static User selectUser(String email) {
        if (email == null || email.isEmpty()) return null;

        String query = "SELECT Email, FirstName, LastName FROM Users WHERE Email = ?";
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getString("FirstName"),
                            rs.getString("LastName"),
                            rs.getString("Email")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Select failed: " + e.getMessage());
        }
        return null;
    }
}
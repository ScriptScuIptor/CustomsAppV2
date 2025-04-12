package database;

import model.User;

import java.sql.*;

public class DatabaseManager {
    private static final String URL = "jdbc:postgresql://localhost:5432/customs";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";

    // Метод для добавления нового пользователя
    public static boolean addUser(String username, String password, String role) {
        // Проверка длины пароля для пользователей, кроме admin
        if (!role.equals("admin") && password.length() < 8) {
            System.out.println("❌ Пароль должен содержать минимум 8 символов.");
            return false;
        }

        String query = "INSERT INTO users (username, password, role_id) " +
                "SELECT ?, ?, r.id FROM roles r WHERE r.role_name = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);  // Не хешируем пароль
            pstmt.setString(3, role);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Метод для поиска пользователя по имени
    public static User findUserByUsername(String username) {
        String query = "SELECT u.id, u.username, u.password, r.role_name " +
                "FROM users u JOIN roles r ON u.role_id = r.id " +
                "WHERE u.username = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),  // Пароль теперь хранится в открытом виде
                        rs.getString("role_name")
                );
            }
        } catch (SQLException e) {
            System.out.println("❌ Ошибка при поиске пользователя: " + e.getMessage());
        }
        return null;
    }

    public static boolean updateUserRole(String username, String newRole) {
        String query = "UPDATE users SET role_id = (SELECT id FROM roles WHERE role_name = ?) " +
                "WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, newRole);
            pstmt.setString(2, username);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Метод для добавления водителя
    public static void addDriver(String name, String licenseNumber) {
        String query = "INSERT INTO drivers (full_name, license_number) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, name);
            pstmt.setString(2, licenseNumber);
            pstmt.executeUpdate();
            System.out.println("✅ Водитель " + name + " добавлен в базу данных.");
        } catch (SQLException e) {
            System.out.println("❌ Ошибка при добавлении водителя: " + e.getMessage());
        }
    }
}

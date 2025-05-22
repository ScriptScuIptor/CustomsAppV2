package CourseWork.Infrastracture.Database.Repositories;

import CourseWork.Core.CourseWork.Domain.Interfaces.IUserRepository;
import CourseWork.Core.CourseWork.Domain.User;

import java.sql.*;
import java.util.*;

public class UserRepository implements IUserRepository {
    private final DbContext dbContext;

    public UserRepository(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public boolean add(User user) {
        String query = "INSERT INTO users (\"UserId\", \"username\", \"password\", \"role\", \"TransportId\", \"UserPassword\") VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setObject(1, user.UserId);
            stmt.setString(2, user.username);
            stmt.setString(3, user.password);
            stmt.setInt(4, user.role);
            stmt.setObject(5, user.TransportId);
            stmt.setString(6, user.UserPassword);

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Ошибка при добавлении пользователя: " + e.getMessage());
            return false;
        }
    }

    public Optional<User> getById(UUID id) {
        String query = "SELECT * FROM users WHERE \"UserId\" = ?";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapUser(rs));
            }
        } catch (SQLException e) {
            System.out.println("❌ Ошибка при получении пользователя: " + e.getMessage());
        }
        return Optional.empty();
    }

    public Optional<User> getByUsername(String username) {
        String query = "SELECT * FROM users WHERE \"username\" = ?";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapUser(rs));
            }
        } catch (SQLException e) {
            System.out.println("❌ Ошибка при поиске пользователя: " + e.getMessage());
        }
        return Optional.empty();
    }

    public List<User> getAll() {
        String query = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        try (Connection conn = dbContext.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                users.add(mapUser(rs));
            }
        } catch (SQLException e) {
            System.out.println("❌ Ошибка при получении всех пользователей: " + e.getMessage());
        }
        return users;
    }

    public boolean update(User user) {
        String query = "UPDATE users SET \"username\"=?, \"password\"=?, \"role\"=?, \"TransportId\"=?, \"UserPassword\"=? WHERE \"UserId\"=?";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, user.username);
            stmt.setString(2, user.password);
            stmt.setInt(3, user.role);
            stmt.setObject(4, user.TransportId);
            stmt.setString(5, user.UserPassword);
            stmt.setObject(6, user.UserId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Ошибка при обновлении пользователя: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(UUID id) {
        String query = "DELETE FROM users WHERE \"UserId\"=?";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setObject(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Ошибка при удалении пользователя: " + e.getMessage());
            return false;
        }
    }

    private User mapUser(ResultSet rs) throws SQLException {
        return new User(
                UUID.fromString(rs.getString("UserId")),
                rs.getString("username"),
                rs.getString("password"),
                rs.getInt("role"),
                (UUID) rs.getObject("TransportId"),
                rs.getString("UserPassword")
        );
    }
}

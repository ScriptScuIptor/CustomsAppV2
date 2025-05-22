package CourseWork.Infrastracture.Database.Repositories;

import CourseWork.Core.CourseWork.Application.UseCases.UserInterfaces.ILoginAttemptRepository;
import CourseWork.Core.CourseWork.Domain.LoginAttempt;

import java.sql.*;
import java.util.*;

public class LoginAttemptRepository implements ILoginAttemptRepository {
    private final DbContext dbContext;

    public LoginAttemptRepository(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    @Override
    public boolean add(LoginAttempt attempt) {
        String query = "INSERT INTO loginattempts (attemptid, username, timestamp, success) VALUES (?, ?, ?, ?)";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, attempt.AttemptId);
            stmt.setString(2, attempt.Username);
            stmt.setTimestamp(3, Timestamp.valueOf(attempt.Timestamp));
            stmt.setBoolean(4, attempt.Success);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Ошибка при добавлении попытки входа: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<LoginAttempt> getById(UUID id) {
        String query = "SELECT * FROM loginattempts WHERE attemptid = ?";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(map(rs));
            }
        } catch (SQLException e) {
            System.out.println("❌ Ошибка при получении попытки входа: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<LoginAttempt> getAll() {
        String query = "SELECT * FROM loginattempts";
        List<LoginAttempt> list = new ArrayList<>();
        try (Connection conn = dbContext.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            System.out.println("❌ Ошибка при получении списка попыток: " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<LoginAttempt> getByUsername(String username) {
        String query = "SELECT * FROM loginattempts WHERE username = ?";
        List<LoginAttempt> list = new ArrayList<>();
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            System.out.println("❌ Ошибка при поиске попыток входа: " + e.getMessage());
        }
        return list;
    }

    @Override
    public boolean delete(UUID id) {
        String query = "DELETE FROM loginattempts WHERE attemptid = ?";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Ошибка при удалении попытки: " + e.getMessage());
            return false;
        }
    }

    private LoginAttempt map(ResultSet rs) throws SQLException {
        return new LoginAttempt(
                UUID.fromString(rs.getString("attemptid")),
                rs.getString("username"),
                rs.getBoolean("success"),
                rs.getTimestamp("timestamp").toLocalDateTime()
        );
    }
}

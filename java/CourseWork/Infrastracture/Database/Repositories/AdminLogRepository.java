package CourseWork.Infrastracture.Database.Repositories;

import CourseWork.Core.CourseWork.Application.UseCases.UserInterfaces.IAdminLogRepository;
import CourseWork.Core.CourseWork.Domain.AdminLog;

import java.sql.*;
import java.util.*;

public class AdminLogRepository implements IAdminLogRepository {
    private final DbContext dbContext;

    public AdminLogRepository(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    @Override
    public boolean add(AdminLog log) {
        String query = "INSERT INTO adminlogs (logid, action, timestamp, \"PerformedBy\") VALUES (?, ?, ?, ?)";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setObject(1, log.LogId);
            stmt.setString(2, log.Action);
            stmt.setTimestamp(3, Timestamp.valueOf(log.Timestamp)); 
            stmt.setString(4, log.PerformedBy);

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Ошибка при добавлении лога: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<AdminLog> getById(UUID id) {
        String query = "SELECT * FROM adminlogs WHERE logid = ?";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(map(rs));
            }
        } catch (SQLException e) {
            System.out.println("❌ Ошибка при получении лога: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<AdminLog> getAll() {
        String query = "SELECT * FROM adminlogs";
        List<AdminLog> list = new ArrayList<>();
        try (Connection conn = dbContext.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            System.out.println("❌ Ошибка при получении логов: " + e.getMessage());
        }
        return list;
    }

    @Override
    public boolean delete(UUID id) {
        String query = "DELETE FROM adminlogs WHERE logid = ?";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Ошибка при удалении лога: " + e.getMessage());
            return false;
        }
    }

    private AdminLog map(ResultSet rs) throws SQLException {
        return new AdminLog(
                UUID.fromString(rs.getString("logid")),
                rs.getString("action"),
                rs.getString("performedby"),
                rs.getTimestamp("timestamp").toLocalDateTime()
        );
    }
}

package CourseWork.Infrastracture.Database.Repositories;

import CourseWork.Core.CourseWork.Domain.Interfaces.IRequestRepository;
import CourseWork.Core.CourseWork.Domain.Request;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RequestRepository implements IRequestRepository {
    private final DbContext dbContext;

    public RequestRepository(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    @Override
    public boolean add(Request request) {
        String query = "INSERT INTO requests (\"RequestId\", \"UserId\", \"Description\", \"Approved\") VALUES (?, ?, ?, ?)";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setObject(1, request.RequestId);
            stmt.setObject(2, request.UserId);
            stmt.setString(3, request.Description);
            stmt.setBoolean(4, request.Approved);

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Ошибка при добавлении запроса: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<Request> getById(UUID id) {
        String query = "SELECT * FROM requests WHERE \"RequestId\" = ?";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(map(rs));
            }
        } catch (SQLException e) {
            System.out.println("❌ Ошибка при получении запроса: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Request> getAll() {
        List<Request> list = new ArrayList<>();
        String query = "SELECT * FROM requests";
        try (Connection conn = dbContext.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            System.out.println("❌ Ошибка при получении списка заявок: " + e.getMessage());
        }
        return list;
    }

    @Override
    public boolean update(Request request) {
        String query = "UPDATE requests SET \"Description\" = ?, \"Approved\" = ? WHERE \"RequestId\" = ?";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, request.Description);
            stmt.setBoolean(2, request.Approved);
            stmt.setObject(3, request.RequestId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Ошибка при обновлении запроса: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(UUID id) {
        String query = "DELETE FROM requests WHERE \"RequestId\" = ?";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Ошибка при удалении запроса: " + e.getMessage());
            return false;
        }
    }

    private Request map(ResultSet rs) throws SQLException {
        return new Request(
                UUID.fromString(rs.getString("RequestId")),
                rs.getString("Description"),
                rs.getBoolean("Approved"),
                UUID.fromString(rs.getString("UserId"))
        );
    }
}

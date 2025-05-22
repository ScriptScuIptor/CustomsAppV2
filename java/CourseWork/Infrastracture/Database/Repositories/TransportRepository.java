package CourseWork.Infrastracture.Database.Repositories;

import CourseWork.Core.CourseWork.Domain.Interfaces.ITransportRepository;
import CourseWork.Core.CourseWork.Domain.Transport;

import java.sql.*;
import java.util.*;

public class TransportRepository implements ITransportRepository {
    private final DbContext dbContext;

    public TransportRepository(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public boolean add(Transport transport) {
        String query = "INSERT INTO transports (\"TransportId\", \"VehicleNumber\", \"IsRegistered\", \"categoryid\") VALUES (?, ?, ?, ?)";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setObject(1, transport.TransportId);
            stmt.setString(2, transport.VehicleNumber);
            stmt.setBoolean(3, transport.IsRegistered);
            stmt.setObject(4, transport.CategoryId);

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Ошибка при добавлении транспорта: " + e.getMessage());
            return false;
        }
    }

    public Optional<Transport> getById(UUID id) {
        String query = "SELECT * FROM transports WHERE \"TransportId\" = ?";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapTransport(rs));
            }
        } catch (SQLException e) {
            System.out.println("❌ Ошибка при получении транспорта: " + e.getMessage());
        }
        return Optional.empty();
    }

    public List<Transport> getAll() {
        String query = "SELECT * FROM transports";
        List<Transport> transports = new ArrayList<>();
        try (Connection conn = dbContext.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                transports.add(mapTransport(rs));
            }
        } catch (SQLException e) {
            System.out.println("❌ Ошибка при получении списка транспорта: " + e.getMessage());
        }
        return transports;
    }

    public boolean update(Transport transport) {
        String query = "UPDATE transports SET \"VehicleNumber\" = ?, \"IsRegistered\" = ? WHERE \"TransportId\" = ?";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, transport.VehicleNumber);
            stmt.setBoolean(2, transport.IsRegistered);
            stmt.setObject(3, transport.TransportId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Ошибка при обновлении транспорта: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(UUID id) {
        String query = "DELETE FROM transports WHERE \"TransportId\" = ?";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setObject(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Ошибка при удалении транспорта: " + e.getMessage());
            return false;
        }
    }

    // 🔄 Новый метод для регистрации транспортного средства
    public boolean registerTransport(UUID transportId) {
        String query = "UPDATE transports SET \"IsRegistered\" = true WHERE \"TransportId\" = ?";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setObject(1, transportId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Ошибка при регистрации транспорта: " + e.getMessage());
            return false;
        }
    }

    private Transport mapTransport(ResultSet rs) throws SQLException {
        return new Transport(
                UUID.fromString(rs.getString("TransportId")),
                rs.getString("VehicleNumber"),
                rs.getBoolean("IsRegistered"),
                rs.getObject("CategoryId") != null ? UUID.fromString(rs.getString("CategoryId")) : null
        );
    }

}

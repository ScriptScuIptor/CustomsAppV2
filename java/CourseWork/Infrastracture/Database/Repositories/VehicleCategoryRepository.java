package CourseWork.Infrastracture.Database.Repositories;


import CourseWork.Core.CourseWork.Application.UseCases.UserInterfaces.IVehicleCategoryRepository;
import CourseWork.Core.CourseWork.Domain.VehicleCategory;

import java.sql.*;
import java.util.*;

public class VehicleCategoryRepository implements IVehicleCategoryRepository {
    private final DbContext dbContext;

    public VehicleCategoryRepository(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    @Override
    public boolean add(VehicleCategory category) {
        String query = "INSERT INTO VehicleCategories (\"CategoryId\", \"Name\", \"Description\") VALUES (?, ?, ?)";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, category.CategoryId);
            stmt.setString(2, category.CategoryName);
            stmt.setString(3, category.Description);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Ошибка при добавлении категории: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<VehicleCategory> getById(UUID id) {
        String query = "SELECT * FROM VehicleCategories WHERE \"CategoryId\" = ?";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(map(rs));
            }
        } catch (SQLException e) {
            System.out.println("❌ Ошибка при получении категории: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<VehicleCategory> getAll() {
        String query = "SELECT * FROM VehicleCategories";
        List<VehicleCategory> list = new ArrayList<>();
        try (Connection conn = dbContext.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            System.out.println("❌ Ошибка при получении категорий: " + e.getMessage());
        }
        return list;
    }

    @Override
    public boolean update(VehicleCategory category) {
        String query = "UPDATE VehicleCategories SET \"Name\" = ?, \"Description\" = ? WHERE \"CategoryId\" = ?";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, category.CategoryName);
            stmt.setString(2, category.Description);
            stmt.setObject(3, category.CategoryId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Ошибка при обновлении категории: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(UUID id) {
        String query = "DELETE FROM VehicleCategories WHERE \"CategoryId\" = ?";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Ошибка при удалении категории: " + e.getMessage());
            return false;
        }
    }

    private VehicleCategory map(ResultSet rs) throws SQLException {
        return new VehicleCategory(
                UUID.fromString(rs.getString("CategoryId")),
                rs.getString("Name"),
                null
        );
    }
}

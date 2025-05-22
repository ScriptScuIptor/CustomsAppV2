package CourseWork.Core.CourseWork.Application.UseCases.UserInterfaces;

import CourseWork.Core.CourseWork.Domain.VehicleCategory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IVehicleCategoryRepository {
    boolean add(VehicleCategory category);
    Optional<VehicleCategory> getById(UUID id);
    List<VehicleCategory> getAll();
    boolean update(VehicleCategory category);
    boolean delete(UUID id);
}

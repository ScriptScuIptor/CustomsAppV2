package CourseWork.Core.CourseWork.Application.UseCases.UserInterfaces;

import CourseWork.Core.CourseWork.Domain.AdminLog;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IAdminLogRepository {
    boolean add(AdminLog log);
    Optional<AdminLog> getById(UUID id);
    List<AdminLog> getAll();
    boolean delete(UUID id);
}
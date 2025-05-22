package CourseWork.Core.CourseWork.Application.UseCases.UserInterfaces;

import CourseWork.Core.CourseWork.Domain.LoginAttempt;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ILoginAttemptRepository {
    boolean add(LoginAttempt attempt);
    Optional<LoginAttempt> getById(UUID id);
    List<LoginAttempt> getAll();
    List<LoginAttempt> getByUsername(String username);
    boolean delete(UUID id);
}
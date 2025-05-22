package CourseWork.Core.CourseWork.Domain.Interfaces;

import CourseWork.Core.CourseWork.Domain.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserRepository {
    boolean add(User user);
    Optional<User> getById(UUID id);
    Optional<User> getByUsername(String username);
    List<User> getAll();
    boolean update(User user);
    boolean delete(UUID id);
}

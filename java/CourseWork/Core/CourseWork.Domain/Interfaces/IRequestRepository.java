package CourseWork.Core.CourseWork.Domain.Interfaces;

import CourseWork.Core.CourseWork.Domain.Request;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRequestRepository {
    boolean add(Request request);
    Optional<Request> getById(UUID id);
    List<Request> getAll();
    boolean update(Request request);
    boolean delete(UUID id);
}
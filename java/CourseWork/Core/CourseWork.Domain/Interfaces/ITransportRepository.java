package CourseWork.Core.CourseWork.Domain.Interfaces;

import CourseWork.Core.CourseWork.Domain.Transport;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ITransportRepository {
    boolean add(Transport transport);
    Optional<Transport> getById(UUID id);
    List<Transport> getAll();
    boolean update(Transport transport);
    boolean delete(UUID id);
}

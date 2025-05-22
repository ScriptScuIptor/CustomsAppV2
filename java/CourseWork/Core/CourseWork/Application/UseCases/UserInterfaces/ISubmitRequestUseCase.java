package CourseWork.Core.CourseWork.Application.UseCases.UserInterfaces;

import CourseWork.Core.CourseWork.Domain.Request;

import java.util.List;
import java.util.UUID;

public interface ISubmitRequestUseCase {
    boolean submit(String description, UUID userId);
    List<Request> getRequestsByUser(UUID userId);
}
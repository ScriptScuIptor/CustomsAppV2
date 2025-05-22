package CourseWork.Core.CourseWork.Application.UseCases.UserInterfaces;

import java.util.UUID;

public interface IDeleteUserUseCase {
    boolean deleteUser(UUID userId);
}
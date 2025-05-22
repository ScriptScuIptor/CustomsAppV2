package CourseWork.Core.CourseWork.Application.UseCases.UserInterfaces;

import CourseWork.Core.CourseWork.Domain.User;

import java.util.List;

public interface IViewAllUsersUseCase {
    List<User> getAllUsers();
}

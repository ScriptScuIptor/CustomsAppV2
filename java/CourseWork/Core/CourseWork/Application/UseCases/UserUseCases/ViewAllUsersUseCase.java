package CourseWork.Core.CourseWork.Application.UseCases.UserUseCases;

import CourseWork.Core.CourseWork.Application.UseCases.UserInterfaces.IViewAllUsersUseCase;
import CourseWork.Core.CourseWork.Domain.Interfaces.IUserRepository;
import CourseWork.Core.CourseWork.Domain.User;

import java.util.List;

public class ViewAllUsersUseCase implements IViewAllUsersUseCase {
    private final IUserRepository userRepository;

    public ViewAllUsersUseCase(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAll();
    }
}
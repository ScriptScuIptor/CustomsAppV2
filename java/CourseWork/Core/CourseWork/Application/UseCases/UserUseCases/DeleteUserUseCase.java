package CourseWork.Core.CourseWork.Application.UseCases.UserUseCases;

import CourseWork.Core.CourseWork.Application.UseCases.UserInterfaces.IDeleteUserUseCase;
import CourseWork.Core.CourseWork.Domain.Interfaces.IUserRepository;

import java.util.UUID;

public class DeleteUserUseCase implements IDeleteUserUseCase {
    private final IUserRepository userRepository;

    public DeleteUserUseCase(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean deleteUser(UUID userId) {
        return userRepository.delete(userId);
    }
}
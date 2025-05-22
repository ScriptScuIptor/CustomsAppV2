package CourseWork.Core.CourseWork.Application.UseCases.UserUseCases;

import CourseWork.Core.CourseWork.Application.UseCases.UserInterfaces.IAuthenticateUserUseCase;
import CourseWork.Core.CourseWork.Domain.Interfaces.IUserRepository;
import CourseWork.Core.CourseWork.Domain.Roles;
import CourseWork.Core.CourseWork.Domain.User;
import CourseWork.InputChecks.AuthenticationResult;

import java.util.Optional;

public class AuthenticateUserUseCase implements IAuthenticateUserUseCase {
    private final IUserRepository userRepository;

    public AuthenticateUserUseCase(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public AuthenticationResult authenticate(String username, String password) {
        Optional<User> optionalUser = userRepository.getByUsername(username);

        if (optionalUser.isEmpty()) {
            return AuthenticationResult.failure("❌ Пользователь не найден.");
        }

        User user = optionalUser.get();

        boolean isAdmin = user.role == Roles.ADMIN.getValue();
        if (!isAdmin && password.length() < 8) {
            return AuthenticationResult.failure("❌ Пароль должен содержать минимум 8 символов.");
        }

        if (!user.password.equals(password)) {
            return AuthenticationResult.failure("❌ Неверный пароль.");
        }

        return AuthenticationResult.success(user);
    }
}

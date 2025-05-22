package CourseWork.Core.CourseWork.Application.UseCases.UserUseCases;

import CourseWork.Core.CourseWork.Application.UseCases.UserInterfaces.IRegisterUserUseCase;
import CourseWork.Core.CourseWork.Domain.Roles;
import CourseWork.Core.CourseWork.Domain.User;
import CourseWork.Core.CourseWork.Domain.Interfaces.IUserRepository;
import CourseWork.InputChecks.RegistrationResult;

import java.util.UUID;

public class RegisterUserUseCase implements IRegisterUserUseCase {
    private final IUserRepository userRepository;

    public RegisterUserUseCase(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public RegistrationResult register(String username, String password, String roleInput) {
        if (userRepository.getByUsername(username).isPresent()) {
            return new RegistrationResult(false, " Пользователь с таким именем уже существует.");
        }

        if (password.length() < 8 && !roleInput.equalsIgnoreCase("ADMIN")) {
            return new RegistrationResult(false, " Пароль должен содержать минимум 8 символов.");
        }

        Roles role = Roles.GUEST; // Жестко задаем роль как GUEST

        User user = new User(
                UUID.randomUUID(),
                username,
                password,
                role.getValue(),
                null,
                password
        );

        boolean added = userRepository.add(user);
        return new RegistrationResult(
                added,
                added ? " Успешно зарегистрирован." : " Ошибка при сохранении пользователя."
        );
    }
}

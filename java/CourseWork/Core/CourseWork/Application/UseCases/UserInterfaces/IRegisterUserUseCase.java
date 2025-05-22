package CourseWork.Core.CourseWork.Application.UseCases.UserInterfaces;

import CourseWork.InputChecks.RegistrationResult;

public interface IRegisterUserUseCase {
    RegistrationResult register(String username, String password, String roleInput);
}

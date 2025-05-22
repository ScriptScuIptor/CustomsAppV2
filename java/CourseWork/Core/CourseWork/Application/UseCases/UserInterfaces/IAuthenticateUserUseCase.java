package CourseWork.Core.CourseWork.Application.UseCases.UserInterfaces;

import CourseWork.InputChecks.AuthenticationResult;

public interface IAuthenticateUserUseCase {
    AuthenticationResult authenticate(String username, String password);
}
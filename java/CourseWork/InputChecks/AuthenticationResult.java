package CourseWork.InputChecks;

import CourseWork.Core.CourseWork.Domain.User;

public class AuthenticationResult {
    public final boolean success;
    public final String message;
    public final User user;

    public AuthenticationResult(boolean success, String message, User user) {
        this.success = success;
        this.message = message;
        this.user = user;
    }

    public static AuthenticationResult failure(String message) {
        return new AuthenticationResult(false, message, null);
    }

    public static AuthenticationResult success(User user) {
        return new AuthenticationResult(true, " Успешный вход как " + user.role, user);
    }
}

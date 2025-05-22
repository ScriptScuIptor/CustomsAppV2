package CourseWork.Server.Serializable.Auth;

import CourseWork.Core.CourseWork.Domain.User;

import java.io.Serializable;

public class AuthResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    public boolean success;
    public String message;
    public User user;

    public AuthResponse(boolean success, String message, User user) {
        this.success = success;
        this.message = message;
        this.user = user;
    }
}

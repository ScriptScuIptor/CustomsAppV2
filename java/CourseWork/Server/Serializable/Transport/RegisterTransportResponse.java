package CourseWork.Server.Serializable.Transport;

import CourseWork.Core.CourseWork.Domain.User;

import java.io.Serializable;

public class RegisterTransportResponse implements Serializable {
    public boolean success;
    public String message;
    public User updatedUser;

    public RegisterTransportResponse(boolean success, String message, User updatedUser) {
        this.success = success;
        this.message = message;
        this.updatedUser = updatedUser;
    }
}

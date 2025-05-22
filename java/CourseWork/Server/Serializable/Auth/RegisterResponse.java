package CourseWork.Server.Serializable.Auth;

import java.io.Serializable;

public class RegisterResponse implements Serializable {
    public boolean success;
    public String message;

    public RegisterResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}

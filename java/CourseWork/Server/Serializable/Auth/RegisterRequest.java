package CourseWork.Server.Serializable.Auth;

import java.io.Serializable;

public class RegisterRequest implements Serializable {
    public String username;
    public String password;

    public RegisterRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
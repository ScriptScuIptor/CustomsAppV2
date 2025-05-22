package CourseWork.Server.Serializable.Auth;

import java.io.Serializable;

public class AuthRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    public String username;
    public String password;

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

package CourseWork.Core.CourseWork.Domain;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    public UUID UserId;
    public String username;
    public String password;
    public int role;
    public UUID TransportId;
    public String UserPassword;

    public User(UUID UserId, String username, String password, int role, UUID TransportId, String UserPassword) {
        this.UserId = UserId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.TransportId = TransportId;
        this.UserPassword = UserPassword;
    }

    // Геттеры для таблицы
    public UUID getUserId() { return UserId; }
    public String getUsername() { return username; }
    public int getRole() { return role; }
}

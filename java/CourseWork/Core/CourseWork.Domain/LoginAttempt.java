package CourseWork.Core.CourseWork.Domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class LoginAttempt implements Serializable {
    private static final long serialVersionUID = 1L;

    public UUID AttemptId;
    public String Username;
    public boolean Success;
    public LocalDateTime Timestamp;

    public LoginAttempt(UUID attemptId, String username, boolean success, LocalDateTime timestamp) {
        this.AttemptId = attemptId;
        this.Username = username;
        this.Success = success;
        this.Timestamp = timestamp;
    }
}

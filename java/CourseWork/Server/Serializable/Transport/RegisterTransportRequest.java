package CourseWork.Server.Serializable.Transport;

import java.io.Serializable;
import java.util.UUID;

public class RegisterTransportRequest implements Serializable {
    public UUID userId;
    public String vehicleNumber;
    public UUID categoryId;
    public String username;
    public String userPassword;

    public RegisterTransportRequest(UUID userId, String vehicleNumber, UUID categoryId, String username, String userPassword) {
        this.userId = userId;
        this.vehicleNumber = vehicleNumber;
        this.categoryId = categoryId;
        this.username = username;
        this.userPassword = userPassword;
    }
}

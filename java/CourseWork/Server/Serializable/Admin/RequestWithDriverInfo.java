package CourseWork.Server.Serializable.Admin;

import CourseWork.Core.CourseWork.Domain.Request;

import java.io.Serializable;
import java.util.UUID;

public class RequestWithDriverInfo implements Serializable {
    public UUID requestId;
    public String description;
    public boolean approved;
    public UUID userId;
    public String driverUsername;
    public String vehicleNumber;
    public String vehicleCategoryName; // ✅ Новое поле

    public RequestWithDriverInfo(UUID requestId, String description, boolean approved, UUID userId,
                                 String driverUsername, String vehicleNumber, String category) {
        this.requestId = requestId;
        this.description = description;
        this.approved = approved;
        this.userId = userId;
        this.driverUsername = driverUsername;
        this.vehicleNumber = vehicleNumber;
        this.vehicleCategoryName = category;
    }

    public Request toRequest() {
        return new Request(requestId, description, approved, userId);
    }
}


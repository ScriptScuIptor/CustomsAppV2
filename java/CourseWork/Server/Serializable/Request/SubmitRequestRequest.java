package CourseWork.Server.Serializable.Request;

import java.io.Serializable;
import java.util.UUID;

public class SubmitRequestRequest implements Serializable {
    public String description;
    public UUID userId;

    public SubmitRequestRequest(String description, UUID userId) {
        this.description = description;
        this.userId = userId;
    }
}

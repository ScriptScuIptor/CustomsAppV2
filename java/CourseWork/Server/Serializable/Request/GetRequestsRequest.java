package CourseWork.Server.Serializable.Request;

import java.io.Serializable;
import java.util.UUID;

public class GetRequestsRequest implements Serializable {
    public UUID userId;

    public GetRequestsRequest(UUID userId) {
        this.userId = userId;
    }
}

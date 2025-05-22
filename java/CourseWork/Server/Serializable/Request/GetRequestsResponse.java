package CourseWork.Server.Serializable.Request;

import CourseWork.Core.CourseWork.Domain.Request;

import java.io.Serializable;
import java.util.List;

public class GetRequestsResponse implements Serializable {
    public List<Request> requests;

    public GetRequestsResponse(List<Request> requests) {
        this.requests = requests;
    }
}

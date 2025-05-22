package CourseWork.Server.Serializable.Request;

import java.io.Serializable;

public class SubmitRequestResponse implements Serializable {
    public boolean success;
    public String message;

    public SubmitRequestResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}

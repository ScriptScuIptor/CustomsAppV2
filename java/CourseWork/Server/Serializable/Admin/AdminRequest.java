package CourseWork.Server.Serializable.Admin;

import CourseWork.Core.CourseWork.Domain.Request;

import java.io.Serializable;
import java.util.UUID;

public class AdminRequest implements Serializable {
    public enum OperationType {
        GET_ALL_USERS,
        GET_ALL_REQUESTS,
        DELETE_USER,
        APPROVE_REQUEST,
        GET_ALL_ATTEMPTS,
        GET_ATTEMPTS_BY_USERNAME,
        GET_ADMIN_LOGS,
        GET_ALL_VEHICLE_CATEGORIES
    }

    public OperationType operationType;
    public UUID userIdToDelete;
    public Request requestToApprove;
    public String usernameToSearch;

    public AdminRequest(OperationType operationType) {
        this.operationType = operationType;
    }

    public AdminRequest(OperationType operationType, UUID userIdToDelete) {
        this.operationType = operationType;
        this.userIdToDelete = userIdToDelete;
    }

    public AdminRequest(OperationType operationType, Request requestToApprove) {
        this.operationType = operationType;
        this.requestToApprove = requestToApprove;
    }

    public AdminRequest(OperationType operationType, String usernameToSearch) {
        this.operationType = operationType;
        this.usernameToSearch = usernameToSearch;
    }
}

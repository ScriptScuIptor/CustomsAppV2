package CourseWork.Server.Serializable.Admin;

import CourseWork.Core.CourseWork.Domain.*;

import java.io.Serializable;
import java.util.List;

public class AdminResponse implements Serializable {
    public boolean success;
    public String message;

    public List<User> users;
    public List<Request> requests;
    public List<RequestWithDriverInfo> requestsWithDriver;
    public List<LoginAttempt> loginAttempts;
    public List<AdminLog> adminLogs;
    public List<VehicleCategory> vehicleCategories;

    public AdminResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public AdminResponse(List<User> users) {
        this.success = true;
        this.users = users;
    }

    public AdminResponse(List<Request> requests, boolean dummy) {
        this.success = true;
        this.requests = requests;
    }

    public AdminResponse(List<RequestWithDriverInfo> enrichedRequests, String ignored, boolean dummy) {
        this.success = true;
        this.requestsWithDriver = enrichedRequests;
    }

    public AdminResponse(List<LoginAttempt> attempts, boolean dummy1, boolean dummy2) {
        this.success = true;
        this.loginAttempts = attempts;
    }

    public AdminResponse(List<AdminLog> logs, boolean dummy1, boolean dummy2, boolean dummy3) {
        this.success = true;
        this.adminLogs = logs;
    }

    public AdminResponse(List<VehicleCategory> categories, boolean dummy1, boolean dummy2, boolean dummy3, boolean dummy4) {
        this.success = true;
        this.vehicleCategories = categories;
    }
}

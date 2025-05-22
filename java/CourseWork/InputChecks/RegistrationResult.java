package CourseWork.InputChecks;

public class RegistrationResult {
    public final boolean success;
    public final String message;

    public RegistrationResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
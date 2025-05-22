package CourseWork.Core.CourseWork.Domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class AdminLog implements Serializable {
    private static final long serialVersionUID = 1L;

    public UUID LogId;
    public String Action;
    public String PerformedBy;
    public LocalDateTime Timestamp;

    public AdminLog(UUID logId, String action, String performedBy, LocalDateTime timestamp) {
        this.LogId = logId;
        this.Action = action;
        this.PerformedBy = performedBy;
        this.Timestamp = timestamp;
    }
}

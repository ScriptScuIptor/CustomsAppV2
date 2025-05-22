package CourseWork.Core.CourseWork.Domain;

import java.io.Serializable;
import java.util.UUID;

public class Request implements Serializable {
    public UUID RequestId;
    public String Description;
    public boolean Approved;
    public UUID UserId;

    public Request(UUID requestId, String description, boolean approved, UUID userId) {
        this.RequestId = requestId;
        this.Description = description;
        this.Approved = approved;
        this.UserId = userId;
    }

    // Геттеры для таблицы
    public String getDescription() { return Description; }
    public boolean isApproved() { return Approved; }
}

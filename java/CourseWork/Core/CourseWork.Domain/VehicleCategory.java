package CourseWork.Core.CourseWork.Domain;

import java.io.Serializable;
import java.util.UUID;

public class VehicleCategory implements Serializable {
    private static final long serialVersionUID = 1L;

    public UUID CategoryId;
    public String CategoryName;
    public String Description;

    public VehicleCategory(UUID categoryId, String categoryName, String description) {
        this.CategoryId = categoryId;
        this.CategoryName = categoryName;
        this.Description = description;
    }

    @Override
    public String toString() {
        return CategoryName;
    }
}

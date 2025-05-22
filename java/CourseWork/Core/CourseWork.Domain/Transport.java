package CourseWork.Core.CourseWork.Domain;

import java.util.UUID;

public class Transport {
    public UUID TransportId;
    public String VehicleNumber;
    public boolean IsRegistered;
    public UUID CategoryId;

    public Transport(UUID transportId, String vehicleNumber, boolean isRegistered, UUID categoryId) {
        this.TransportId = transportId;
        this.VehicleNumber = vehicleNumber;
        this.IsRegistered = isRegistered;
        this.CategoryId = categoryId;
    }

    // Старый конструктор (оставим для совместимости, если где-то ещё используется)
    public Transport(UUID transportId, String vehicleNumber, boolean isRegistered) {
        this(transportId, vehicleNumber, isRegistered, null);
    }
}

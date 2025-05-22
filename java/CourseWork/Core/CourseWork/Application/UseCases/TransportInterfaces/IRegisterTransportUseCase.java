package CourseWork.Core.CourseWork.Application.UseCases.TransportInterfaces;

import java.util.UUID;

public interface IRegisterTransportUseCase {
    boolean registerTransport(UUID userId, String vehicleNumber, UUID categoryId);
}

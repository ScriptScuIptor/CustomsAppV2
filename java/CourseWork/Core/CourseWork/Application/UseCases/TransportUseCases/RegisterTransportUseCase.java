package CourseWork.Core.CourseWork.Application.UseCases.TransportUseCases;

import CourseWork.Core.CourseWork.Application.UseCases.TransportInterfaces.IRegisterTransportUseCase;
import CourseWork.Core.CourseWork.Domain.Interfaces.ITransportRepository;
import CourseWork.Core.CourseWork.Domain.Interfaces.IUserRepository;
import CourseWork.Core.CourseWork.Domain.Roles;
import CourseWork.Core.CourseWork.Domain.Transport;
import CourseWork.Core.CourseWork.Domain.User;

import java.util.Optional;
import java.util.UUID;

public class RegisterTransportUseCase implements IRegisterTransportUseCase {
    private final IUserRepository userRepository;
    private final ITransportRepository transportRepository;

    public RegisterTransportUseCase(IUserRepository userRepository, ITransportRepository transportRepository) {
        this.userRepository = userRepository;
        this.transportRepository = transportRepository;
    }

    @Override
    public boolean registerTransport(UUID userId, String vehicleNumber, UUID categoryId) {
        Optional<User> optionalUser = userRepository.getById(userId);
        if (optionalUser.isEmpty()) {
            return false;
        }

        User user = optionalUser.get();

        UUID transportId = UUID.randomUUID();
        Transport transport = new Transport(transportId, vehicleNumber, true, categoryId); // ✔️ исправлено

        if (!transportRepository.add(transport)) {
            return false;
        }

        user.TransportId = transportId;
        user.role = Roles.DRIVER.getValue();

        return userRepository.update(user);
    }
}

package CourseWork.Server;

import CourseWork.Core.CourseWork.Application.UseCases.TransportInterfaces.IRegisterTransportUseCase;
import CourseWork.Core.CourseWork.Application.UseCases.UserInterfaces.*;
import CourseWork.Core.CourseWork.Domain.*;
import CourseWork.Core.CourseWork.Domain.Interfaces.IRequestRepository;
import CourseWork.Infrastracture.Database.Repositories.*;
import CourseWork.InputChecks.AuthenticationResult;
import CourseWork.InputChecks.RegistrationResult;
import CourseWork.Server.Serializable.Admin.*;
import CourseWork.Server.Serializable.Auth.*;
import CourseWork.Server.Serializable.Transport.*;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.*;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final IAuthenticateUserUseCase authUseCase;
    private final IRegisterUserUseCase registerUseCase;
    private final IRegisterTransportUseCase registerTransportUseCase;
    private final IViewAllUsersUseCase viewUsersUseCase;
    private final IDeleteUserUseCase deleteUserUseCase;
    private final IRequestRepository requestRepository;
    private final TransportRepository transportRepository;
    private final LoginAttemptRepository loginAttemptRepository;
    private final AdminLogRepository adminLogRepository;
    private final VehicleCategoryRepository vehicleCategoryRepository;

    public ClientHandler(
            Socket socket,
            IAuthenticateUserUseCase authUseCase,
            IRegisterUserUseCase registerUseCase,
            IRegisterTransportUseCase registerTransportUseCase,
            IViewAllUsersUseCase viewUsersUseCase,
            IDeleteUserUseCase deleteUserUseCase,
            IRequestRepository requestRepository,
            TransportRepository transportRepository,
            LoginAttemptRepository loginAttemptRepository,
            AdminLogRepository adminLogRepository,
            VehicleCategoryRepository vehicleCategoryRepository
    ) {
        this.socket = socket;
        this.authUseCase = authUseCase;
        this.registerUseCase = registerUseCase;
        this.registerTransportUseCase = registerTransportUseCase;
        this.viewUsersUseCase = viewUsersUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.requestRepository = requestRepository;
        this.transportRepository = transportRepository;
        this.loginAttemptRepository = loginAttemptRepository;
        this.adminLogRepository = adminLogRepository;
        this.vehicleCategoryRepository = vehicleCategoryRepository;
    }

    @Override
    public void run() {
        try (
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())
        ) {
            Object request = in.readObject();

            if (request instanceof AuthRequest authRequest) {
                AuthenticationResult result = authUseCase.authenticate(authRequest.username, authRequest.password);

                loginAttemptRepository.add(new LoginAttempt(
                        UUID.randomUUID(),
                        authRequest.username,
                        result.success,
                        LocalDateTime.now()
                ));

                out.writeObject(new AuthResponse(result.success, result.message, result.user));

            } else if (request instanceof RegisterRequest registerRequest) {
                RegistrationResult result = registerUseCase.register(registerRequest.username, registerRequest.password, "GUEST");
                out.writeObject(new RegisterResponse(result.success, result.message));

            } else if (request instanceof RegisterTransportRequest transportRequest) {
                boolean success = registerTransportUseCase.registerTransport(
                        transportRequest.userId,
                        transportRequest.vehicleNumber,
                        transportRequest.categoryId
                );
                Optional<User> updatedUser = Optional.ofNullable(authUseCase.authenticate(transportRequest.username, transportRequest.userPassword).user);

                out.writeObject(new RegisterTransportResponse(success,
                        success ? "✅ Транспорт зарегистрирован" : "❌ Ошибка при регистрации транспорта",
                        updatedUser.orElse(null)));

            } else if (request instanceof AdminRequest adminRequest) {
                switch (adminRequest.operationType) {
                    case GET_ALL_USERS -> {
                        List<User> users = viewUsersUseCase.getAllUsers();
                        out.writeObject(new AdminResponse(users));
                    }
                    case GET_ALL_REQUESTS -> {
                        List<Request> requests = requestRepository.getAll();
                        List<User> users = viewUsersUseCase.getAllUsers();
                        List<Transport> transports = transportRepository.getAll();
                        List<VehicleCategory> categories = vehicleCategoryRepository.getAll();

                        List<RequestWithDriverInfo> enriched = requests.stream().map(req -> {
                            User user = users.stream().filter(u -> u.UserId.equals(req.UserId)).findFirst().orElse(null);
                            String username = user != null ? user.username : "Неизвестно";

                            Transport transport = transports.stream()
                                    .filter(t -> t.TransportId.equals(user != null ? user.TransportId : null))
                                    .findFirst().orElse(null);

                            String vehicle = transport != null ? transport.VehicleNumber : "Неизвестно";
                            String category = categories.stream()
                                    .filter(c -> transport != null && c.CategoryId.equals(transport.CategoryId))
                                    .map(c -> c.CategoryName)
                                    .findFirst().orElse("Неизвестно");

                            return new RequestWithDriverInfo(req.RequestId, req.Description, req.Approved,
                                    req.UserId, username, vehicle, category);
                        }).toList();

                        out.writeObject(new AdminResponse(enriched, "ENRICHED", true));
                    }
                    case DELETE_USER -> {
                        UUID userId = adminRequest.userIdToDelete;
                        boolean deleted = deleteUserUseCase.deleteUser(userId);

                        if (deleted) {
                            adminLogRepository.add(new AdminLog(
                                    UUID.randomUUID(),
                                    "Удалён пользователь " + userId,
                                    "Админ",
                                    LocalDateTime.now()
                            ));
                        }

                        out.writeObject(new AdminResponse(deleted, deleted ? "✅ Удалено" : "❌ Не удалось удалить"));
                    }
                    case APPROVE_REQUEST -> {
                        Request updatedRequest = adminRequest.requestToApprove;
                        boolean success = requestRepository.update(updatedRequest);

                        if (success) {
                            adminLogRepository.add(new AdminLog(
                                    UUID.randomUUID(),
                                    "Одобрена заявка " + updatedRequest.RequestId,
                                    "Админ",
                                    LocalDateTime.now()
                            ));
                        }

                        out.writeObject(new AdminResponse(success, success ? "✅ Заявка одобрена" : "❌ Не удалось обновить заявку"));
                    }
                    case GET_ALL_ATTEMPTS -> {
                        List<LoginAttempt> attempts = loginAttemptRepository.getAll();
                        out.writeObject(new AdminResponse(attempts, true, true));
                    }
                    case GET_ATTEMPTS_BY_USERNAME -> {
                        List<LoginAttempt> attempts = loginAttemptRepository.getByUsername(adminRequest.usernameToSearch);
                        out.writeObject(new AdminResponse(attempts, true, true));
                    }
                    case GET_ADMIN_LOGS -> {
                        List<AdminLog> logs = adminLogRepository.getAll();
                        out.writeObject(new AdminResponse(logs, true, true, true));
                    }
                    case GET_ALL_VEHICLE_CATEGORIES -> {
                        List<VehicleCategory> categories = vehicleCategoryRepository.getAll();
                        out.writeObject(new AdminResponse(categories, true, true, true, true));
                    }
                }

            } else {
                out.writeObject("❌ Неизвестный тип запроса.");
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("❌ Ошибка клиента: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {}
        }
    }
}

package CourseWork.Server;

import CourseWork.Core.CourseWork.Application.UseCases.TransportUseCases.RegisterTransportUseCase;
import CourseWork.Core.CourseWork.Application.UseCases.UserUseCases.*;
import CourseWork.Infrastracture.Database.Repositories.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    public static void main(String[] args) {
        final int PORT = 5555;

        DbContext dbContext = new DbContext();

        UserRepository userRepo = new UserRepository(dbContext);
        TransportRepository transportRepo = new TransportRepository(dbContext);
        RequestRepository requestRepo = new RequestRepository(dbContext);
        AdminLogRepository adminLogRepo = new AdminLogRepository(dbContext);
        LoginAttemptRepository loginAttemptRepo = new LoginAttemptRepository(dbContext);
        VehicleCategoryRepository vehicleCategoryRepo = new VehicleCategoryRepository(dbContext);

        // Use Cases
        var authUC = new AuthenticateUserUseCase(userRepo);
        var registerUC = new RegisterUserUseCase(userRepo);
        var registerTransportUC = new RegisterTransportUseCase(userRepo, transportRepo);
        var viewUsersUC = new ViewAllUsersUseCase(userRepo);
        var deleteUserUC = new DeleteUserUseCase(userRepo);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("🚀 Сервер запущен на порту " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("🔌 Клиент подключился: " + clientSocket.getInetAddress());

                ClientHandler handler = new ClientHandler(
                        clientSocket,
                        authUC,
                        registerUC,
                        registerTransportUC,
                        viewUsersUC,
                        deleteUserUC,
                        requestRepo,
                        transportRepo,
                        loginAttemptRepo,
                        adminLogRepo,
                        vehicleCategoryRepo
                );

                new Thread(handler).start();
            }

        } catch (IOException e) {
            System.err.println("❌ Ошибка сервера: " + e.getMessage());
        }
    }
}

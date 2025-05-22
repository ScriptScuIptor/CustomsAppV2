package CourseWork.Presentation;

import CourseWork.Core.CourseWork.Application.UseCases.UserInterfaces.IAuthenticateUserUseCase;
import CourseWork.InputChecks.AuthenticationResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class UserAuthentication {
    private final BufferedReader reader;
    private final PrintWriter writer;
    private final IAuthenticateUserUseCase authenticateUserUseCase;

    public UserAuthentication(BufferedReader reader, PrintWriter writer, IAuthenticateUserUseCase authenticateUserUseCase) {
        this.reader = reader;
        this.writer = writer;
        this.authenticateUserUseCase = authenticateUserUseCase;
    }

    public void execute() throws IOException {
        writer.println("Введите имя пользователя:");
        String username = reader.readLine();

        writer.println("Введите пароль:");
        String password = reader.readLine();

        AuthenticationResult result = authenticateUserUseCase.authenticate(username, password);

        if (result.success && result.user != null) {
            writer.println("✅ Успешный вход. Ваша роль: " + result.user.role);
        } else {
            writer.println(result.message); // Показываем конкретную причину
        }
    }
}

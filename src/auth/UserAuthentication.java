package auth;

import database.DatabaseManager;
import model.User;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;

public class UserAuthentication {
    private BufferedReader reader;
    private PrintWriter writer;

    public UserAuthentication(BufferedReader reader, PrintWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public void authenticateUser() throws IOException {
        String username = reader.readLine();  // Чтение имени пользователя
        String password = reader.readLine();  // Чтение пароля

        // Логируем запрос на вход
        System.out.println("Вход пользователя: " + username);

        // Проверка существования пользователя
        User user = DatabaseManager.findUserByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {  // Сравниваем пароль в открытом виде
            writer.println("❌ Неверные учетные данные. Отключение.");
        } else {
            writer.println("✅ Успешный вход. Ваша роль: " + user.getRole());
        }
    }
}

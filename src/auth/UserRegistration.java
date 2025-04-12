package auth;

import database.DatabaseManager;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;

public class UserRegistration {
    private BufferedReader reader;
    private PrintWriter writer;

    public UserRegistration(BufferedReader reader, PrintWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public void registerUser() throws IOException {
        String username = reader.readLine();  // Чтение имени пользователя
        String password = reader.readLine();  // Чтение пароля

        // Логируем запрос на регистрацию
        System.out.println("Регистрация пользователя: " + username);

        // Проверка, существует ли уже такой пользователь
        if (DatabaseManager.findUserByUsername(username) != null) {
            writer.println("❌ Пользователь с таким именем уже существует.");
        } else {
            // Проверка длины пароля (минимум 8 символов, кроме admin)
            if (password.length() < 8) {
                writer.println("❌ Пароль должен содержать минимум 8 символов.");
                return;
            }

            // Сохраняем пароль в открытом виде
            boolean success = DatabaseManager.addUser(username, password, "guest"); // Роль по умолчанию 'guest'
            if (success) {
                writer.println("✅ Регистрация прошла успешно.");
            } else {
                writer.println("❌ Ошибка при регистрации.");
            }
        }
    }
}
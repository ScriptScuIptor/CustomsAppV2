package CourseWork.Client;

import CourseWork.Core.CourseWork.Domain.User;
import CourseWork.Server.Serializable.Auth.AuthRequest;
import CourseWork.Server.Serializable.Auth.AuthResponse;


import java.io.*;
import java.net.Socket;

public class SimpleClient {
    public static void main(String[] args) {
        final String HOST = "localhost";
        final int PORT = 5555;

        try (Socket socket = new Socket(HOST, PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            // 🔐 Ввод данных
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Введите имя пользователя: ");
            String username = reader.readLine();

            System.out.print("Введите пароль: ");
            String password = reader.readLine();

            // 📤 Отправка запроса
            AuthRequest request = new AuthRequest(username, password);
            out.writeObject(request);

            // 📥 Получение ответа
            AuthResponse response = (AuthResponse) in.readObject();

            if (response.success) {
                User user = response.user;
                System.out.println("✅ Успешная авторизация! Роль: " + user.role);
            } else {
                System.out.println("❌ Неверные учетные данные.");
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("❌ Ошибка клиента: " + e.getMessage());
        }
    }
}

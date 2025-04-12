package client;

import java.io.*;
import java.net.Socket;

public class SimpleClient {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 12345;

        try (Socket socket = new Socket(serverAddress, port);
             InputStream input = socket.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(input));
             OutputStream output = socket.getOutputStream();
             PrintWriter writer = new PrintWriter(output, true)) {

            // Прочитаем приветствие и запрос на команду
            System.out.println(reader.readLine());  // "Введите команду: LOGIN или REGISTER"

            // Подаем команду LOGIN или REGISTER
            String command = "REGISTER";  // или "LOGIN", в зависимости от того, что выбрал пользователь
            writer.println(command);

            // Вводим имя пользователя и пароль
            String username = "user";  // Пример
            String password = "password";  // Пример

            // Отправляем данные
            writer.println(username);
            writer.println(password);

            // Получаем ответ от сервера
            System.out.println(reader.readLine());  // Например, "✅ Регистрация прошла успешно."

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

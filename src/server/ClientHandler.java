package server;

import database.DatabaseManager;
import model.User;
import auth.UserRegistration;
import auth.UserAuthentication;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (
                InputStream input = clientSocket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                OutputStream output = clientSocket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true)
        ) {
            // Чтение команды
            String command = reader.readLine();
            System.out.println("Получена команда от клиента: " + command); // Логируем команду

            if ("REGISTER".equalsIgnoreCase(command)) {
                new UserRegistration(reader, writer).registerUser();
            } else if ("LOGIN".equalsIgnoreCase(command)) {
                new UserAuthentication(reader, writer).authenticateUser();
            } else {
                writer.println("❌ Неверная команда. Используйте LOGIN или REGISTER.");
            }

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

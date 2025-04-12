package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;

public class AuthWindow {

    private static JLabel statusLabel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Авторизация");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Имя пользователя:");
        userLabel.setBounds(10, 20, 150, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(160, 20, 165, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Пароль:");
        passwordLabel.setBounds(10, 50, 150, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(160, 50, 165, 25);
        panel.add(passwordText);

        JButton loginButton = new JButton("Войти");
        loginButton.setBounds(10, 80, 150, 25);
        panel.add(loginButton);

        JButton registerButton = new JButton("Зарегистрироваться");
        registerButton.setBounds(170, 80, 150, 25);
        panel.add(registerButton);

        statusLabel = new JLabel("");
        statusLabel.setBounds(10, 110, 300, 25);
        panel.add(statusLabel);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());
                sendDataToServer("LOGIN", username, password);
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());

                // Проверка длины пароля
                if (password.length() < 8) {
                    statusLabel.setText("❌ Пароль должен содержать минимум 8 символов.");
                    return;
                }

                sendDataToServer("REGISTER", username, password);
            }
        });
    }

    private static void sendDataToServer(String command, String username, String password) {
        String serverAddress = "localhost";
        int port = 12345;

        try (Socket socket = new Socket(serverAddress, port);
             InputStream input = socket.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(input));
             OutputStream output = socket.getOutputStream();
             PrintWriter writer = new PrintWriter(output, true)) {

            writer.println(command);
            writer.println(username);
            writer.println(password);

            // Чтение и вывод ответа от сервера
            String response = reader.readLine();
            statusLabel.setText(response);
            System.out.println("Ответ от сервера: " + response);  // Логирование ответа от сервера

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//package client;
//
//import javafx.application.Application;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.layout.*;
//import javafx.stage.Stage;
//
//import java.io.*;
//import java.net.Socket;
//
//public class AuthWindow extends Application {
//
//    private static Label statusLabel;
//
//    public static void main(String[] args) {
//        launch(args);  // Запуск JavaFX
//    }
//
//    @Override
//    public void start(Stage primaryStage) {
//        primaryStage.setTitle("Авторизация");
//
//        // Создание панели для компонентов
//        VBox vbox = new VBox(10);  // Вертикальное размещение с отступами
//        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");
//
//        // Поля ввода для логина и пароля
//        TextField userText = new TextField();
//        userText.setPromptText("Имя пользователя");
//
//        PasswordField passwordText = new PasswordField();
//        passwordText.setPromptText("Пароль");
//
//        // Кнопки
//        Button loginButton = new Button("Войти");
//        loginButton.setStyle("-fx-background-color: #007BFF; -fx-text-fill: white;");
//        loginButton.setOnAction(e -> {
//            String username = userText.getText();
//            String password = passwordText.getText();
//            sendDataToServer("LOGIN", username, password);
//        });
//
//        Button registerButton = new Button("Зарегистрироваться");
//        registerButton.setStyle("-fx-background-color: #28A745; -fx-text-fill: white;");
//        registerButton.setOnAction(e -> {
//            String username = userText.getText();
//            String password = passwordText.getText();
//
//            // Проверка длины пароля
//            if (password.length() < 8) {
//                statusLabel.setText("❌ Пароль должен содержать минимум 8 символов.");
//            } else {
//                sendDataToServer("REGISTER", username, password);
//            }
//        });
//
//        // Статусная метка
//        statusLabel = new Label("");
//        statusLabel.setStyle("-fx-text-fill: red;");
//
//        // Добавление компонентов в панель
//        vbox.getChildren().addAll(userText, passwordText, loginButton, registerButton, statusLabel);
//
//        // Настройка сцены и окна
//        Scene scene = new Scene(vbox, 400, 250);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//    private static void sendDataToServer(String command, String username, String password) {
//        String serverAddress = "localhost";
//        int port = 12345;
//
//        try (Socket socket = new Socket(serverAddress, port);
//             InputStream input = socket.getInputStream();
//             BufferedReader reader = new BufferedReader(new InputStreamReader(input));
//             OutputStream output = socket.getOutputStream();
//             PrintWriter writer = new PrintWriter(output, true)) {
//
//            writer.println(command);  // Отправляем команду (LOGIN или REGISTER)
//            writer.println(username);  // Отправляем логин
//            writer.println(password);  // Отправляем пароль
//
//            // Чтение и вывод ответа от сервера
//            String response = reader.readLine();
//            statusLabel.setText(response);
//            System.out.println("Ответ от сервера: " + response);  // Логирование ответа от сервера
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}

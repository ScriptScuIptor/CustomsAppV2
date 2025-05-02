package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import client.controllers.AuthWindowController;


import java.io.IOException;

public class AuthWindowApp extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        showAuthWindow();  // Показываем окно авторизации
    }

    public void showAuthWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/AuthWindow.fxml"));
        Scene scene = new Scene(loader.load());

        // Получаем контроллер и передаем ему ссылку на AuthWindowApp
        AuthWindowController controller = loader.getController();
        controller.setAuthWindowApp(this);  // Передаем объект в контроллер

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Метод для переключения на окно в зависимости от роли
    public void switchToWindow(String role) throws IOException {
        FXMLLoader loader;
        Scene scene;

        if ("admin".equals(role)) {
            loader = new FXMLLoader(getClass().getResource("/resources/AdminWindow.fxml"));
            scene = new Scene(loader.load());
        } else if ("driver".equals(role)) {
            loader = new FXMLLoader(getClass().getResource("/resources/DriverWindow.fxml"));
            scene = new Scene(loader.load());
        } else {
            loader = new FXMLLoader(getClass().getResource("/resources/GuestWindow.fxml"));
            scene = new Scene(loader.load());
        }

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);  // Запуск JavaFX
    }
}

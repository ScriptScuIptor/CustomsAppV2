package CourseWork.Presentation;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        MainAppManager.showAuthWindow(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
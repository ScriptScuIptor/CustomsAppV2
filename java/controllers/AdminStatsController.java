package controllers;

import CourseWork.Presentation.NavigationHelper;
import CourseWork.Server.Serializable.Admin.AdminRequest;
import CourseWork.Server.Serializable.Admin.AdminResponse;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

import java.io.*;
import java.net.Socket;

public class AdminStatsController {
    @FXML private PieChart requestPieChart;
    @FXML private Label statusLabel;

    private final String HOST = "localhost";
    private final int PORT = 5555;

    @FXML
    public void initialize() {
        loadStats();
    }

    // 👇 Метод возврата назад
    @FXML
    private void onBack() {
        NavigationHelper.goBack(statusLabel); // любой @FXML-элемент подойдёт как Node
    }

    private void loadStats() {
        try (Socket socket = new Socket(HOST, PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject(new AdminRequest(AdminRequest.OperationType.GET_ALL_REQUESTS));
            AdminResponse response = (AdminResponse) in.readObject();

            if (response.success && response.requestsWithDriver != null) {
                long approved = response.requestsWithDriver.stream().filter(r -> r.approved).count();
                long unapproved = response.requestsWithDriver.size() - approved;

                PieChart.Data approvedData = new PieChart.Data("Одобренные (" + approved + ")", approved);
                PieChart.Data unapprovedData = new PieChart.Data("Неодобренные (" + unapproved + ")", unapproved);

                requestPieChart.setData(FXCollections.observableArrayList(approvedData, unapprovedData));
                statusLabel.setText("");

                // Отложенный стиль подписей
                Platform.runLater(() -> {
                    for (PieChart.Data data : requestPieChart.getData()) {
                        Node label = data.getNode().lookup(".chart-pie-label");
                        if (label != null) {
                            label.setStyle("-fx-fill: white; -fx-font-weight: bold;");
                        }
                    }
                });
            }

        } catch (IOException | ClassNotFoundException e) {
            statusLabel.setText("❌ Ошибка загрузки статистики");
            e.printStackTrace();
        }
    }

}

//package server;
//
//import database.DatabaseManager;
//
//import java.io.*;
//import java.net.Socket;
//
//public class DriverHandler {
//    private Socket socket;
//    private String username;
//
//    public DriverHandler(Socket socket, String username) {
//        this.socket = socket;
//        this.username = username;
//    }
//
//    public void handle() {
//        try (
//                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)
//        ) {
//            writer.println("🔧 Доступны команды: REGISTER_TRANSPORT [номер_ТС] [номер_лицензии]");
//
//            String inputLine;
//            while ((inputLine = reader.readLine()) != null) {
//                String[] parts = inputLine.trim().split(" ");
//                if (parts.length == 3 && "REGISTER_TRANSPORT".equalsIgnoreCase(parts[0])) {
//                    String vehicleNumber = parts[1];
//                    String licenseNumber = parts[2];
//
//                    int driverId = DatabaseManager.getDriverIdByLicense(licenseNumber);
//                    if (driverId == -1) {
//                        writer.println("❌ Водитель с такой лицензией не найден.");
//                    } else {
//                        boolean added = DatabaseManager.registerTransport(vehicleNumber, driverId);
//                        if (added) {
//                            writer.println("✅ Транспорт зарегистрирован: " + vehicleNumber);
//                            DatabaseManager.updateUserRole(username, "driver");
//                            writer.println("🔄 Ваша роль обновлена на: driver");
//                        } else {
//                            writer.println("❌ Ошибка при регистрации транспорта.");
//                        }
//                    }
//                } else {
//                    writer.println("❓ Неизвестная команда.");
//                }
//            }
//
//            socket.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}

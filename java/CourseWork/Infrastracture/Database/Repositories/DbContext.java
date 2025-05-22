package CourseWork.Infrastracture.Database.Repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbContext {
    private static final String URL = "jdbc:postgresql://localhost:5432/Customs";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";

    public Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
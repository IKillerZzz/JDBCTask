package database;

import java.sql.*;

public class Main {
    private final static String URL = "jdbc:postgresql://localhost:5432/library";
    private final static String USER = "postgres";
    private final static String PASS = "postgres";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASS)) {
            if (connection != null) {
                Statement statement = connection.createStatement();
                Library library = new Library(statement);
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}

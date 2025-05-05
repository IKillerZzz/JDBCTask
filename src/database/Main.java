package database;

import java.sql.*;

/*
✅Создать базу данных library.
✅Создать таблицы:
✅authors: id (PK), first_name, last_name
✅books: id (PK), title, author_id, published_year
✅readers: id (PK), first_name, last_name, email
✅Связать читателей и книги (тут полет фантазии, как вы это сделаете)
✅Написать код для подключения к базе данных с использованием JDBC

Написать метод для добавления новой книги в таблицу books
Написать метод для добавления нового читателя в таблицу readers
Написать метод для извлечения всех книг из таблицы books
Написать метод для извлечения всех читателей из таблицы readers
Написать метод для извлечения всех авторов из таблицы authors
Написать метод для обновления информации о книге по её id
Написать метод для удаления книги по её id
Написать метод, который добавляет книгу и автора в одну транзакцию, в случае ошибки откатить изменения
Написать метод для поиска книг по названию или автору
*/

public class Main {
    private final static String URL = "jdbc:postgresql://localhost:5432/library";
    private final static String USER = "postgres";
    private final static String PASS = "kmp32sda";

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

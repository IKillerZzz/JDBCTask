package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Library {
    private Connection connection;
    private static String createTableAuthors = "CREATE TABLE IF NOT EXISTS authors " +
            "(id SERIAL NOT NULL, " +
            " first_name VARCHAR(30), " +
            " last_name VARCHAR (30), " +
            " PRIMARY KEY (id))";

    private static String createTableBooks = "CREATE TABLE IF NOT EXISTS books " +
            "(id SERIAL NOT NULL, " +
            " title VARCHAR(50), " +
            " author_id SMALLINT, " +
            " published_year SMALLINT, " +
            " FOREIGN KEY (author_id) REFERENCES authors(id), " +
            " PRIMARY KEY (id))";

    private static String createTableReaders = "CREATE TABLE IF NOT EXISTS readers " +
            "(id SERIAL NOT NULL, " +
            " first_name VARCHAR(30), " +
            " last_name VARCHAR(30), " +
            " email VARCHAR(50), " +
            " PRIMARY KEY (id))";

    private static String createTableBorrowing = "CREATE TABLE IF NOT EXISTS borrowing " +
            "(id SERIAL NOT NULL, " +
            " reader_id SMALLINT, " +
            " book_id SMALLINT, " +
            " borrowed_date DATE, " +
            " isReturned BOOLEAN DEFAULT FALSE, " +
            " FOREIGN KEY (reader_id) REFERENCES readers(id), " +
            " FOREIGN KEY (book_id) REFERENCES books(id), " +
            " PRIMARY KEY (id))";

    public Library(Connection connection) throws SQLException {
        this.connection = connection;
        Statement statement = connection.createStatement();

        statement.executeUpdate(createTableAuthors);
        statement.executeUpdate(createTableBooks);
        statement.executeUpdate(createTableReaders);
        statement.executeUpdate(createTableBorrowing);
    }

    public void addNewAuthor(String first_name, String last_name) throws SQLException {
        String insertIntoAuthors = "INSERT INTO authors (first_name, last_name) Values (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertIntoAuthors);
        preparedStatement.setString(1, first_name);
        preparedStatement.setString(2, last_name);
        preparedStatement.executeUpdate();
    }

    public void addNewBook(String title, int  author_id, int published_year) throws SQLException {
        String insertIntoBooks = "INSERT INTO books (title, author_id, published_year) Values (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertIntoBooks);
        preparedStatement.setString(1, title);
        preparedStatement.setInt(2, author_id);
        preparedStatement.setInt(3, published_year);
        preparedStatement.executeUpdate();
    }

    public void addNewReader(String first_name, String last_name, String  email) throws SQLException {
        String insertIntoReaders = "INSERT INTO readers (first_name, last_name, email) Values (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertIntoReaders);
        preparedStatement.setString(1, first_name);
        preparedStatement.setString(2, last_name);
        preparedStatement.setString(3, email);
        preparedStatement.executeUpdate();
    }
}

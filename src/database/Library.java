package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private Connection connection;
    private Statement statement;
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
        this.statement = connection.createStatement();

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

    public List<Books> getAllBooks() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM books");
        List<Books> list = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String title = resultSet.getString("title");
            int author_id = resultSet.getInt("author_id");
            int published_year = resultSet.getInt("published_year");
            list.add(new Books(id, title, author_id, published_year));
        }
        printInfo(list);
        return list;
    }

    public List<Readers> getAllReaders() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM readers");
        List<Readers> list = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String first_name = resultSet.getString("first_name");
            String last_name = resultSet.getString("last_name");
            String email = resultSet.getString("email");
            list.add(new Readers(id, first_name, last_name, email));
        }
        printInfo(list);
        return list;
    }

    public List<Authors> getAllAuthors() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM authors");
        List<Authors> list = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String first_name = resultSet.getString("first_name");
            String last_name = resultSet.getString("last_name");
            list.add(new Authors(id, first_name, last_name));
        }
        printInfo(list);
        return list;
    }

    public <E> void printInfo(List<E> list) {
        for (E element : list) {
            System.out.println(element);
        }
    }
}

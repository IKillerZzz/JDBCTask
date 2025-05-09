package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    public void addNewAuthor(String firstName, String lastName) throws SQLException {
        String insertIntoAuthors = "INSERT INTO authors (first_name, last_name) Values (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertIntoAuthors);
        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, lastName);
        preparedStatement.executeUpdate();
    }

    public void addNewBook(String title, int  authorId, int publishedYear) throws SQLException {
        String insertIntoBooks = "INSERT INTO books (title, author_id, published_year) Values (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertIntoBooks);
        preparedStatement.setString(1, title);
        preparedStatement.setInt(2, authorId);
        preparedStatement.setInt(3, publishedYear);
        preparedStatement.executeUpdate();
    }

    public void addNewReader(String firstName, String lastName, String  email) throws SQLException {
        String insertIntoReaders = "INSERT INTO readers (first_name, last_name, email) Values (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertIntoReaders);
        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, lastName);
        preparedStatement.setString(3, email);
        preparedStatement.executeUpdate();
    }

    public List<Books> getAllBooks() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM books");
        List<Books> list = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String title = resultSet.getString("title");
            int authorId = resultSet.getInt("author_id");
            int publishedYear = resultSet.getInt("published_year");
            list.add(new Books(id, title, authorId, publishedYear));
        }
        printInfo(list);
        return list;
    }

    public List<Readers> getAllReaders() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM readers");
        List<Readers> list = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String email = resultSet.getString("email");
            list.add(new Readers(id, firstName, lastName, email));
        }
        printInfo(list);
        return list;
    }

    public List<Authors> getAllAuthors() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM authors");
        List<Authors> list = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            list.add(new Authors(id, firstName, lastName));
        }
        printInfo(list);
        return list;
    }

    public void updateBookById(int id) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        String title = null;
        Integer year = null;
        String updateBookQuery = null;

        System.out.println("Хотите изменить название книги? Введите 'да' или 'нет'");
        String answerForTitle = scanner.nextLine();
        if(answerForTitle.equalsIgnoreCase("да")) {
            System.out.println("Введите новое название книги:");
            title = scanner.nextLine();
        }

        System.out.println("Хотите изменить год издания книги? Введите 'да' или 'нет'");
        String answerForYear = scanner.nextLine();
        if (answerForYear.equalsIgnoreCase("да")) {
            System.out.println("Введите новый год издания книги:");
            year = scanner.nextInt();
        }
        scanner.close();

        if (answerForTitle.equalsIgnoreCase("да") && answerForYear.equalsIgnoreCase("да")) {
            updateBookQuery = String.format("UPDATE books SET title = '%s', published_year = %d WHERE id = %d", title, year, id);
        } else if (answerForTitle.equalsIgnoreCase("да")) {
            updateBookQuery = String.format("UPDATE books SET title = '%s' WHERE id = %d", title, year, id);
        } else if (answerForYear.equalsIgnoreCase("да")) {
            updateBookQuery = String.format("UPDATE books SET published_year = %d WHERE id = %d", title, year, id);
        }

        if (updateBookQuery != null) {
            int status = statement.executeUpdate(updateBookQuery);

            if (status != 0) {
                System.out.printf("Информация о книге с id: %d успешно обновлена!%n", id);
            } else {
                System.out.printf("Ошибка! Книги с id: %d нет в базе данных!%n", id);
            }
        }
    }

    public void deleteBookById(int id) throws SQLException {

        int status = statement.executeUpdate("DELETE FROM books WHERE Id = " + id);

        if (status != 0) {
            System.out.printf("Книга с id: %d успешно удалена!%n", id);
        } else {
            System.out.printf("Ошибка! Книги с id: %d нет в базе данных!%n", id);
        }
    }

    public void addNewAuthorAndBook(String firstName, String lastName, String title, int  authorId, int publishedYear) throws SQLException {
        try{
            connection.setAutoCommit(false);
            addNewAuthor(firstName, lastName);
            addNewBook(title, authorId, publishedYear);
            System.out.println("Транзакция успешно завершена!");
            connection.commit();
        }
        catch (Exception e) {
            System.out.println("Ошибка! Транзакция не выполнена! Откат изменений!");
            connection.rollback();
        }
        finally {
            connection.setAutoCommit(true);
        }
    }

    public List<Books> findBooksByTitle(String bookTitle) throws SQLException {
        ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM books WHERE title = '%s'", bookTitle));
        List<Books> list = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String title = resultSet.getString("title");
            int authorId = resultSet.getInt("author_id");
            int publishedYear = resultSet.getInt("published_year");
            list.add(new Books(id, title, authorId, publishedYear));
        }
        printInfo(list);
        return list;
    }

    public List<Books> findBooksByAuthor(String authorFirstName, String authorLastName) throws SQLException {
        ResultSet resultSet = statement.executeQuery(String.format(
                "SELECT * " +
                "FROM books " +
                "JOIN authors ON books.author_id = authors.id " +
                "WHERE authors.first_name = '%s' AND authors.last_name = '%s'"
                , authorFirstName, authorLastName));
        List<Books> list = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String title = resultSet.getString("title");
            int authorId = resultSet.getInt("author_id");
            int publishedYear = resultSet.getInt("published_year");
            list.add(new Books(id, title, authorId, publishedYear));
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

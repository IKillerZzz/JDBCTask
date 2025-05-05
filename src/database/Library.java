package database;

import java.sql.SQLException;
import java.sql.Statement;

public class Library {
    private static String createTableAuthors = "CREATE TABLE authors " +
            "(id SERIAL NOT NULL, " +
            " first_name VARCHAR(30), " +
            " last_name VARCHAR (30), " +
            " PRIMARY KEY (id))";

    private static String createTableBooks = "CREATE TABLE books " +
            "(id SERIAL NOT NULL, " +
            " title VARCHAR(50), " +
            " author_id SMALLINT, " +
            " published_year SMALLINT, " +
            " FOREIGN KEY (author_id) REFERENCES authors(id), " +
            " PRIMARY KEY (id))";

    private static String createTableReaders = "CREATE TABLE readers " +
            "(id SERIAL NOT NULL, " +
            " first_name VARCHAR(30), " +
            " last_name VARCHAR(30), " +
            " email VARCHAR(50), " +
            " PRIMARY KEY (id))";

    private static String createTableBorrowing = "CREATE TABLE borrowing " +
            "(id SERIAL NOT NULL, " +
            " reader_id SMALLINT, " +
            " book_id SMALLINT, " +
            " borrowed_date DATE, " +
            " isReturned BOOLEAN DEFAULT FALSE, " +
            " FOREIGN KEY (reader_id) REFERENCES readers(id), " +
            " FOREIGN KEY (book_id) REFERENCES books(id), " +
            " PRIMARY KEY (id))";

    public Library(Statement statement) throws SQLException {
        statement.executeUpdate(createTableAuthors);
        statement.executeUpdate(createTableBooks);
        statement.executeUpdate(createTableReaders);
        statement.executeUpdate(createTableBorrowing);
    }
}

package database;

import java.sql.Date;

public class Borrowing {
    private int id;
    private int reader_id;
    private int book_id;
    private Date borrowed_date;
    boolean isReturned;

    public Borrowing(int id, int reader_id, int book_id, Date borrowed_date, boolean isReturned) {
        this.id = id;
        this.reader_id = reader_id;
        this.book_id = book_id;
        this.borrowed_date = borrowed_date;
        this.isReturned = false;
    }

    @Override
    public String toString() {
        return "id: " + id +
                ", reader_id: " + reader_id +
                ", book_id: " + book_id +
                ", borrowed_date: " + borrowed_date.toString() +
                ", isReturned: " + isReturned;
    }
}

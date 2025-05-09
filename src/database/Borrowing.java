package database;

import java.sql.Date;

public class Borrowing {
    private int id;
    private int readerId;
    private int bookId;
    private Date borrowedDate;
    boolean isReturned;

    public Borrowing(int id, int readerId, int bookId, Date borrowedDate, boolean isReturned) {
        this.id = id;
        this.readerId = readerId;
        this.bookId = bookId;
        this.borrowedDate = borrowedDate;
        this.isReturned = false;
    }

    @Override
    public String toString() {
        return "id: " + id +
                ", reader_id: " + readerId +
                ", book_id: " + bookId +
                ", borrowed_date: " + borrowedDate.toString() +
                ", isReturned: " + isReturned;
    }
}

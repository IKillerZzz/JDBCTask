package database;

public class Books {
    private int id;
    private String title;
    private int authorId;
    private int publishedYear;

    public Books(int id, String title, int authorId, int publishedYear) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.publishedYear = publishedYear;
    }

    @Override
    public String toString() {
        return "id: " + id +
                ", title: " + title +
                ", author_id: " + authorId +
                ", published_year: " + publishedYear;
    }
}

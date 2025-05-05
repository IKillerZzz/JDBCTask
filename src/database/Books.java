package database;

public class Books {
    private int id;
    private String title;
    private int author_id;
    private int published_year;

    public Books(int id, String title, int author_id, int published_year) {
        this.id = id;
        this.title = title;
        this.author_id = author_id;
        this.published_year = published_year;
    }

    @Override
    public String toString() {
        return "id: " + id +
                ", title: " + title +
                ", author_id: " + author_id +
                ", published_year: " + published_year;
    }
}

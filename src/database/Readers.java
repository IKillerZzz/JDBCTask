package database;

public class Readers {
    private int id;
    private String firstName;
    private String lastName;
    private String email;

    public Readers(int id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Override
    public String toString() {
        return "id: " + id +
                ", firstName: " + firstName +
                ", lastName: " + lastName +
                ", email: " + email;
    }
}

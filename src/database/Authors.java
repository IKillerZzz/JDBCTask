package database;

public class Authors {
    private int id;
    private String firstName;
    private String lastName;

    public Authors(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "id: " + id +
                ", firstName: " + firstName +
                ", lastName: " + lastName;
    }
}

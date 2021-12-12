package model;

public abstract class User extends BaseModel {

    private String firstName;
    private String lastName;


    public User(int id, String firstName, String lastName) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Method used when insering in the db

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}

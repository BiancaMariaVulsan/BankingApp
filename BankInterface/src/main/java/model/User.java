package model;

public abstract class User extends BaseModel {

    private String firstName;
    private String lastName;
    private String cnp;

    public User(int id, String firstName, String lastName, String cnp) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.cnp = cnp;
    }

    // Method used when insering in the db

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCnp() { return cnp; }
}

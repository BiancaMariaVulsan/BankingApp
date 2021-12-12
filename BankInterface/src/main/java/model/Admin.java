package model;


import java.sql.Date;

public class Admin extends User {

    Date date; // TODO: admin => birthdate > 18
    public Admin(int id, String firstName, String lastName, Date date) {
        super(id, firstName, lastName);
        this.date = date;
    }

    // Actions an admin can perform

    public void seeAllUsers() {

    }

    public void seeAllAccounts() {

    }

    public void openUserAccount() {

    }

    public void closeUserAccount() {

    }

    public Date getDate() {
        return date;
    }
}

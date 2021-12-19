package model;


import repository.AccHolderRepository;

import java.sql.Date;
import java.util.ArrayList;

public class Admin extends User {

    Date date; // TODO: admin => birthdate > 18
    public Admin(int id, String firstName, String lastName, Date date, String cnp) {
        super(id, firstName, lastName, cnp);
        this.date = date;
    }

    // Actions an admin can perform

    public ArrayList<User> seeAllUsers() {
        AccHolderRepository accHolderRepository = new AccHolderRepository();
        ArrayList<User> users = accHolderRepository.selectAllEntities();
        return users;
    }

    public void createUser(AccHolder userToCreate) {
        AccHolderRepository accHolderRepository = new AccHolderRepository();
        accHolderRepository.insertEntity(userToCreate);
    }

    public void deleteUser(AccHolder userToDelete) {
        AccHolderRepository accHolderRepository = new AccHolderRepository();
        accHolderRepository.deleteEntity(userToDelete);
    }

    public void createUserAccount(AccHolder user, Account account) {
        user.createAccount(account);
    }

    public void closeUserAccount(AccHolder user, Account account) {
        user.closeAccount(account);
    }

    public void makeTransaction(Account sender, Account receiver, double amount, String description) {
        sender.transfer(receiver, amount, description);
    }

    public Date getDate() {
        return date;
    }
}

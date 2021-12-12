package model;

import repository.CurrentAccRepository;
import repository.SavingsAccRepository;

import java.util.ArrayList;

public class AccHolder extends User{

    private final SavingsAccRepository savingsAccRepository = new SavingsAccRepository();
    private final CurrentAccRepository currentAccRepository = new CurrentAccRepository();

    private String cnp;
    private String userName;
    private String password;

    // TODO: inialize the accounts when creating a user without entering inf loop
    private ArrayList<Account> accounts = new ArrayList<>();

    public AccHolder(int id, String firstName, String lastName, String cnp, String userName, String password) {
        super(id, firstName, lastName);
        this.cnp = cnp;
        this.userName = userName;
        this.password = password;
    }

    // Actions an account holder can perform

    public void createAccount(Account account) {
        accounts.add(account);
        // add in db
        if(account instanceof Savings)
            savingsAccRepository.insertEntity(account);
        else
            currentAccRepository.insertEntity(account);
    }

    public void closeAccount(Account account) {
        accounts.remove(account);
        // remove from db
        if(account instanceof Savings)
            savingsAccRepository.deleteEntity(account);
        else
            currentAccRepository.deleteEntity(account);
    }

    public void seeAccountDetails() {

    }

    public void changeUserName() {

    }

    public void changePassword() {

    }

    // TODO: if it is not used on other purposes than for testing the program, delete it
    public ArrayList<Account> getAccounts() { return accounts; }

    public String getCnp() {
        return cnp;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}

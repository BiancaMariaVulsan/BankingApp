package model;

import repository.CurrentAccRepository;
import repository.SavingsAccRepository;

import java.util.ArrayList;

public class AccHolder extends User{

    private final SavingsAccRepository savingsAccRepository = new SavingsAccRepository();
    private final CurrentAccRepository currentAccRepository = new CurrentAccRepository();

    private String userName;
    private String password;

    private ArrayList<Account> accounts = new ArrayList<>();

    public AccHolder(int id, String firstName, String lastName, String cnp, String userName, String password) {
        super(id, firstName, lastName, cnp);
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

    public ArrayList<Account> getAccounts() {
        accounts = currentAccRepository.selectByUserId(id);
        accounts.addAll(savingsAccRepository.selectByUserId(id));
        return accounts;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}

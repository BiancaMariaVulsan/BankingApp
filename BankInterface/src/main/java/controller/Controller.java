package controller;

import model.*;
import repository.*;
import view.AdminController;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;

public class Controller {
    AccHolderRepository accHolderRepository ;
    AdminRepository adminRepository;
    CategoryRepository categoryRepository;
    CurrentAccRepository currentAccRepository;
    SavingsAccRepository savingsAccRepository;
    TransactionRepository transactionRepository;

    public Controller(AccHolderRepository accHolderRepository, AdminRepository adminRepository, CategoryRepository categoryRepository, CurrentAccRepository currentAccRepository, SavingsAccRepository savingsAccRepository, TransactionRepository transactionRepository) {
        this.accHolderRepository = accHolderRepository;
        this.adminRepository = adminRepository;
        this.categoryRepository = categoryRepository;
        this.currentAccRepository = currentAccRepository;
        this.savingsAccRepository = savingsAccRepository;
        this.transactionRepository = transactionRepository;
    }

    public boolean checkUserExists(String username, String password){
        return accHolderRepository.checkIfUserExists(username, password);
    }

    public void addAccountHolder(String firstName, String lastName, String CNP, String username, String password){
        AccHolder accHolder = new AccHolder(1,firstName,lastName,CNP,username,password);
        if (!accHolderRepository.checkIfUserExists(username,password))
            accHolderRepository.insertEntity(accHolder);
        else
            throw new RuntimeException("There is already an account holder with this username");
    }
    public void addAccountSavings(double initialDeposit, AccHolder accHolder){
        if (initialDeposit < 50)
            throw new RuntimeException("You have to deposit at least 50$ initially!");
        Savings savings = new Savings(1, accHolder, initialDeposit);
        System.out.println(savings);
//        if(!savingsAccRepository.checkIfObjectAlreadyExists(savings)) {
//        }
//        else {
//            System.out.println("Already exists");
//        }
        accHolder.createAccount(savings);

    }
    public void addAccountCurrent(double initialDeposit, AccHolder accHolder){
        if (initialDeposit < 50)
            throw new RuntimeException("You have to deposit at least 50$ initially!");
        Current current = new Current(1,accHolder,initialDeposit);
        System.out.println(current);
//        if(!currentAccRepository.checkIfObjectAlreadyExists(current)) {
//        }
//        else {
//            System.out.println("Already exists");
//        }
        accHolder.createAccount(current);
    }
    public void addTransaction(Account senderAcc, String reciever, double value, String descriprion) {
        Account receiverAcc = savingsAccRepository.selectByAccountNr(reciever);
        if(receiverAcc == null) {
            receiverAcc = currentAccRepository.selectByAccountNr(reciever);
        }
        if (receiverAcc == null)
            throw new RuntimeException("There is no reciever with this account number");
        else if(senderAcc.id == receiverAcc.id) {
            throw new RuntimeException("Please select a different receiver");
        }
        // add transaction to repo
        senderAcc.transfer(receiverAcc, value, descriprion);
    }

    public ArrayList<Account> getAccountsByUser(AccHolder accHolder){
        return accHolder.getAccounts();
    }

    public ArrayList<Savings> getSavingsByUser(AccHolder accHolder){
        return accHolder.getSavings();
    }
    public ArrayList<Current> getCurrentsByUser(AccHolder accHolder){
        return accHolder.getCurrents();
    }

    public ArrayList<Transaction> getAllTransactionsByUser(AccHolder accHolder){
        ArrayList<Account> accounts = accHolder.getAccounts();
        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.clear();
        for (Account account : accounts){
            transactions.addAll(account.getTransactions());
        }
        return transactions;
    }

    public ArrayList<Transaction> getTransactionsByAccount(Account account){
        return account.getTransactions();
    }

    public ArrayList<Category> getAllCategoriesName(){
        return categoryRepository.selectAllEntities();
    }

    public int getNumberOfTransactionByCategory(AccHolder accHolder,Category category){
        ArrayList<Account> accounts = accHolder.getAccounts();
        int totalCount = 0;
        for (Account account : accounts) {
             ArrayList<Transaction> transactions = account.getTransactionsByCategory(category);
             totalCount+= transactions.size();
        }
        return totalCount;
    }

    public void makeDeposit(Account account,double amount){
        account.deposit(amount);
    }
    public void makeWithdrawal(Account account,double amount){
        account.withdraw(amount);
    }

    public ArrayList<AccHolder> getAllAccountHolders(){
        return accHolderRepository.selectAllEntities();
    }

    public void removeAccountHolder(AccHolder accHolder){
        accHolderRepository.deleteEntity(accHolder);
    }

    public void removeAccountByAccountHolder(AccHolder accHolder, Account account){
        accHolder.closeAccount(account);
    }

    public AccHolder getAccHolderByUsername(String userName) {
        return accHolderRepository.selectByUsername(userName);
    }

}

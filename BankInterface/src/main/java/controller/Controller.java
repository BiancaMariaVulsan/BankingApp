package controller;

import model.*;
import repository.*;
import view.AdminController;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;

public class Controller {
    AccHolderRepository accHolderRepository ;
    AdminController adminController;
    CategoryRepository categoryRepository;
    CurrentAccRepository currentAccRepository;
    SavingsAccRepository savingsAccRepository;
    TransactionRepository transactionRepository;

    public Controller(AccHolderRepository accHolderRepository, AdminController adminController, CategoryRepository categoryRepository, CurrentAccRepository currentAccRepository, SavingsAccRepository savingsAccRepository, TransactionRepository transactionRepository) {
        this.accHolderRepository = accHolderRepository;
        this.adminController = adminController;
        this.categoryRepository = categoryRepository;
        this.currentAccRepository = currentAccRepository;
        this.savingsAccRepository = savingsAccRepository;
        this.transactionRepository = transactionRepository;
    }

    public boolean checkUserExists(String username, String password){
        // todo check by username and password
        return false;
    }

    public void addAccountHolder(String firstName, String lastName, String CNP, String username, String password){
        AccHolder accHolder = new AccHolder(1,firstName,lastName,CNP,username,password);
        if (!accHolderRepository.checkIfObjectAlreadyExists(accHolder))
            accHolderRepository.insertEntity(accHolder);
        else
            throw new RuntimeException("There is already an account holder with this username");
    }
    public void addAccountSavings(double initialDeposit, AccHolder accHolder){
        if (initialDeposit < 0)
            throw new RuntimeException("The initial deposit must be positive ! ");
        Savings savings = new Savings(1,accHolder,initialDeposit);
        accHolder.createAccount(savings);
    }
    public void addAccountCurrent(double initialDeposit, AccHolder accHolder){
        if (initialDeposit < 0)
            throw new RuntimeException("The initial deposit must be positive ! ");
        Current current = new Current(1,accHolder,initialDeposit);
        accHolder.createAccount(current);
    }
    public void addTransaction(Account account, String reciever){
        // todo select by account number
        // add transaction to repo
    }

    public ArrayList<Account> getAccountsByUser(AccHolder accHolder){
        return accHolder.getAccounts();
    }

    public ArrayList<Savings> getSavingsByUser(AccHolder accHolder){
        ArrayList<Account> accounts = accHolder.getAccounts();
        ArrayList<Savings> savings = new ArrayList<>();
        for (Account account :  accounts){
            if (account instanceof Savings)
                savings.add((Savings) account);
        }
        return savings;
    }
    public ArrayList<Current> getCurrentsByUser(AccHolder accHolder){
        ArrayList<Account> accounts = accHolder.getAccounts();
        ArrayList<Current> currents = new ArrayList<>();
        for (Account account :  accounts){
            if (account instanceof Current)
                currents.add((Current) account);
        }
        return currents;
    }

    public ArrayList<Transaction> getAllTransactionsByUser(AccHolder accHolder){
        ArrayList<Account> accounts = accHolder.getAccounts();
        ArrayList<Transaction> transactions = new ArrayList<>();
        for (Account account : accounts){
            transactions.addAll(account.getTransactions());
        }
        return transactions;
    }

    public ArrayList<Category> getAllCategoriesName(){
        ArrayList<Category> categories = categoryRepository.selectAllEntities();
        return categories;
    }

    public int getNumberOfTransactionByCategory(AccHolder accHolder,Category category){
        ArrayList<Account> accounts = accHolder.getAccounts();
        int totalCount = 0;
        for (Account account : accounts){
         ArrayList<Transaction> transactions = account.getTransactionsByCategory(category);
         totalCount+= transactions.size();
        }
        return totalCount;
    }

    public ArrayList<AccHolder> getAllAccountHolders(){
        return accHolderRepository.selectAllEntities();
    }

    public void removeAccountHolder(AccHolder accHolder){
        accHolderRepository.deleteEntity(accHolder);
    }

    public void removeSavingsByAccountHolder(AccHolder accHolder, Account account){
        accHolder.closeAccount(account);
    }

}
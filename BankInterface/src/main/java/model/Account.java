package model;

import repository.SavingsAccRepository;
import repository.TransactionRepository;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public abstract class Account extends BaseModel implements IRate {

    private ArrayList<Transaction> transactions = new ArrayList<>();
    private AccHolder accHolder;

    private double balance;
    private Category category;
    private Date date;

    protected String accNumber;
    protected double rate;

    /**
     * Constructor used when reading from db
     */
    public Account(int id, AccHolder accHolder, double initalDeposit, Category category, String accNumber) {
        super(id);
        this.accHolder = accHolder;
        this.category = category;
        balance = initalDeposit;
        this.accNumber = accNumber;
    }

    /**
     * Constructor used when inserting in db
     */
    public Account(int id, AccHolder accHolder, double initalDeposit) {
        super(id);
        this.accHolder = accHolder;
        this.category = new Category(5, "other");
        balance = initalDeposit;
        init();
    }

    private void init()
    {
        // Set the account number
        this.accNumber = setAccountNumber();
        setRate();
    }

    public abstract void setRate();

    public abstract double getRate();

    public double getBalance() { return balance; }

    public String getAccNumber() { return accNumber; }

    public AccHolder getAccHolder() { return accHolder; }

    public ArrayList<Transaction> getTransactions() {
        // initialize the list of transactions (read them from db)
        TransactionRepository transactionRepository = new TransactionRepository();
        transactions.clear();
        transactions = transactionRepository.selectByUserId(this.id);
        return transactions;
    }
    public Category getCategory() { return category; }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    private String setAccountNumber() {
        LocalDate localDate = LocalDate.now();
        date = java.sql.Date.valueOf(localDate);
        String cnp = accHolder.getCnp();
        char firstName = accHolder.getFirstName().charAt(0);
        char lastName = accHolder.getLastName().charAt(0);
        return Character.toString(firstName) + Character.toString(lastName) + date.getTime();
    }

    public void compound() {
        double accruedInterest = balance * (rate/100);
        balance = balance + accruedInterest;
        System.out.println("Accrued Intereset: " + accruedInterest);
        printBalance();
    }

    public void deposit(double amount) {
        balance = balance + amount;

        SavingsAccRepository savingsAccRepository = new SavingsAccRepository(); // could also be current
        savingsAccRepository.updateEntity(this, "balance", this.balance);

        System.out.println("Depositing " + amount);
        printBalance();
    }

    public void withdraw(double amount) {
        if(balance - amount >= 0) {
            balance = balance - amount;

            SavingsAccRepository savingsAccRepository = new SavingsAccRepository(); // could also be current
            savingsAccRepository.updateEntity(this, "balance", this.balance);

            System.out.println("Withdrawing " + amount);
            printBalance();

        } else {
            System.out.println("Make sure that you have enough money");
        }
    }

    public void transfer(Account receiver, double amount, String description) {
        if(this instanceof Savings) {
            if(this.getTransactions().size() >= 6) {
                System.out.println("This is a savings account! You cannot make more than 6 transactions on it!");
                return;
            }
        }
        if(this != receiver) {
            if(amount <= balance) {
                balance = balance - amount;
                double receiverBalance = receiver.getBalance();
                receiver.setBalance(receiverBalance + amount);
                LocalDate localDate = LocalDate.now();
                Date date = java.sql.Date.valueOf(localDate);
                Transaction transaction = new Transaction(transactions.size()+1, this, receiver, amount, date);
                transaction.setDescription(description);
                transactions.add(transaction);
                // add in db
                TransactionRepository transactionRepository = new TransactionRepository();
                transactionRepository.insertEntity(transaction);
                SavingsAccRepository savingsAccRepository = new SavingsAccRepository(); // could also be current
                savingsAccRepository.updateEntity(this, "balance", this.balance);
                savingsAccRepository.updateEntity(receiver, "balance", receiver.getBalance());

                System.out.println("Transfer " + amount + " to " + receiver);
                printBalance();
            } else {
                throw new RuntimeException("Please make sure that you have enough money");
            }
        } else {
            throw new RuntimeException("Please choose a different receiver!");
        }
    }

    @Override
    public String toString() {
        return this.accNumber.toString()+","+this.category.getName()+","+String.valueOf(this.balance)+","+String.valueOf(this.rate);
    }

    public ArrayList<Transaction> getTransactionsByCategory(Category category) {
        ArrayList<Transaction> transactionsByCateg = new ArrayList<>();
        ArrayList<Transaction> transactions = getTransactions();
        for (Transaction transaction : transactions) {
            if(transaction.getCategory().id == category.id) {
                transactionsByCateg.add(transaction);
            }
        }
        return transactionsByCateg;
    }

    public void printBalance() {
        System.out.println("Your balance is now " + balance);
    }

    public void showInfo() {        System.out.println(
                "first name: " + accHolder.getFirstName() + "\nlast name: " + accHolder.getLastName() +
                "\naccouny number: " + accNumber +
                "\nbalance: " + balance +
                "\nrate: " + rate + "%"
        );
    }
}

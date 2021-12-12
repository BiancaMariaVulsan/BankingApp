package model;

import repository.TransactionRepository;

import java.util.ArrayList;

public abstract class Account extends BaseModel implements IRate {

    private ArrayList<Transaction> transactions = new ArrayList<>();
    private AccHolder accHolder;
    private double balance;

    protected String accNumber;
    protected double rate;

    static int index = 10000;

    public Account(int id, AccHolder accHolder, double initalDeposit) {
        super(id);
        this.accHolder = accHolder;
        balance = initalDeposit;
        init();
        // initialize the list of transactions (read them from db)
        TransactionRepository transactionRepository = new TransactionRepository();
        transactions = transactionRepository.selectAllEntities();
    }

    private void init()
    {
        // Set the account number
        index++;
        this.accNumber = setAccountNumber();
        setRate();
    }

    public abstract void setRate();
    public abstract double getRate();
    public double getBalance() { return balance; }
    public String getAccNumber() { return accNumber; }
    public AccHolder getAccHolder() { return accHolder; }

    private String setAccountNumber() {
        String cnp = accHolder.getCnp();
        String lastTwoCnp = cnp.substring(cnp.length()-2);
        int uniqueId = index;
        int randomNumber = (int) (Math.random() * Math.pow(10,3));
        return lastTwoCnp + uniqueId + randomNumber;
    }

    public void compound() {
        double accruedInterest = balance * (rate/100);
        balance = balance + accruedInterest;
        System.out.println("Accrued Intereset: " + accruedInterest);
        printBalance();
    }

    public void deposit(double amount) {
        balance = balance + amount;
        System.out.println("Depositing " + amount);
        printBalance();
    }

    public void withdraw(double amount) {
        balance = balance - amount;
        System.out.println("Withdrawing " + amount);
        printBalance();
    }

    public void transfer(String receiver, double amount, String description) {
        balance = balance - amount;

        Transaction transaction = new Transaction(transactions.size()+1); // TODO: check if the id ok
        transaction.setValue(amount);
        transaction.setReceiver(receiver);
        transaction.setDescription(description);
        transactions.add(transaction);

        System.out.println("Transfer " + amount + " to " + receiver);
        printBalance();
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

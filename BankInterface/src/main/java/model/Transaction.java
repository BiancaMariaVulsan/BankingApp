package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Transaction extends BaseModel {

    private double value;
    private Account sender;
    private Account receiver;
    private String description;
    private Category category;

    // TODO: VAD CE TIP DE DATA SA PUN
    private Date date;

    /**
     * Constructor used when reading a transaction from the db
     */
    public Transaction(int id, Account sender, Account receiver, double value, Date date, Category category) {
        super(id);
        this.sender = sender;
        this.receiver = receiver;
        this.value = value;
        this.date = date;
        this.category = category;
    }

    /**
     * Constructor used when creating a transaction to insert in db
     */
    public Transaction(int id, Account sender, Account receiver, double value, Date date) {
        super(id);
        this.sender = sender;
        this.receiver = receiver;
        this.value = value;
        this.date = date;
        this.category = receiver.getCategory();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getValue() { return value; }
    public Account getSender() { return sender; }
    public Category getCategory() { return category; }
    public Account getReceiver() { return receiver; }
    public Date getDate() { return date; }
}

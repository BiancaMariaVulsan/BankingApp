package model;

public class Transaction extends BaseModel {

    private double value;
    private String receiver;
    private String description;
    // DateTime date = new DateTime();

    public Transaction(int id) {
        super(id);
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

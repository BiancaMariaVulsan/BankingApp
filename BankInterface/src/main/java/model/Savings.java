package model;

public class Savings extends Account {

    private int safetyDepositBoxId;
    private int safetyDepositBoxKey;

    /**
     * Constructor used when inserting in db
     */
    public Savings(int id, AccHolder accHolder, double initialDeposit) {
        super(id, accHolder, initialDeposit);
        init();
        setRate();
    }

    /**
     * Constructor used when reading from db
     */
    public Savings(int id, AccHolder accHolder, double initialDeposit, int safetyDepositBoxId, int safetyDepositBoxKey, Category category, String accountNr) {
        super(id, accHolder, initialDeposit, category, accountNr);
        this.safetyDepositBoxId = safetyDepositBoxId;
        this.safetyDepositBoxKey = safetyDepositBoxKey;
        setRate();
    }

    @Override
    public String toString() {
        return super.toString()+","+safetyDepositBoxId;
    }

    private void init()
    {
        accNumber = "1" + accNumber;
        setSafetyDepositBox();
    }

    @Override
    public void setRate() {
        rate = getBaseRate() - 0.25;
    }
    @Override
    public double getRate() { return rate; }

    public int getSafetyDepositBoxId() { return safetyDepositBoxId; }

    public int getSafetyDepositBoxKey() { return safetyDepositBoxKey; }

    private void setSafetyDepositBox () {
        safetyDepositBoxId = (int)(Math.random() * Math.pow(10,3));
        safetyDepositBoxKey = (int)(Math.random() * Math.pow(10,4));
    }

    public void showInfo() {
        System.out.println("Accouny type: savings");
        super.showInfo();
        System.out.println(
                "Savings account features" +
                "\n id: " + safetyDepositBoxId +
                "\n key: " + safetyDepositBoxKey
                );
    }
}

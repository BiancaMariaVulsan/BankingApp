package model;

public class Savings extends Account {

    private int safetyDepositBoxId;
    private int safetyDepositBoxKey;

    public Savings(int id, AccHolder accHolder, double initialDeposit) {
        super(id, accHolder, initialDeposit);
        init();
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

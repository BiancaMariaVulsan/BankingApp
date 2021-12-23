package model;

public class Current extends Account {

    private int debitCardNr;
    private int debitCardPIN;

    /**
     * Constructor used when reading from db
     */
    public Current(int id, AccHolder accHolder, double initialDeposit, int debitCardNr, int debitCardPIN , Category category, String accountNr) {
        super(id, accHolder, initialDeposit, category, accountNr);
        this.debitCardNr = debitCardNr;
        this.debitCardPIN = debitCardPIN;
    }

    /**
     * Constructor used when inserting into db
     */
    public Current(int id, AccHolder accHolder, double initialDeposit) {
        super(id, accHolder, initialDeposit);
        init();
    }

    private void init()
    {
        accNumber = "2" + accNumber;
        setDebitCard();
    }

    /**
     *  the rate for the current account is 15% from the base rate
     */
    @Override
    public void setRate() {
        rate = getBaseRate() * 0.15;
    }
    @Override
    public double getRate() { return rate; }

    public int getDebitCardNr() { return debitCardNr; }

    public int getDebitCardPIN() { return debitCardPIN; }

    private void setDebitCard () {
        debitCardNr = (int)(Math.random() * Math.pow(10,12));
        debitCardPIN = (int)(Math.random() * Math.pow(10,4));
    }

    public void showInfo() {
        System.out.println("Accouny type: current");
        super.showInfo();
        System.out.println(
                "Savings account features" +
                        "\n nr: " + debitCardNr +
                        "\n pin: " + debitCardPIN
        );
    }
}

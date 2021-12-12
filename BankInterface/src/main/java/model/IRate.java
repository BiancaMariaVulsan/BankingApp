package model;

public interface IRate {

    /**
     * Method that returns the base rate
     */
    default double getBaseRate() {
        return 2.5;
    }
}

package repository;

import model.AccHolder;
import model.Account;
import model.Savings;
import model.Transaction;

import java.sql.ResultSet;
import java.util.ArrayList;

public class SavingsAccRepository extends Repository {

    private ArrayList<Savings> savingAccounts = new ArrayList<>();

    /**
     * / Constructor used to send the name of the corresponding table from db
     */
    public SavingsAccRepository() { super("savingsacc"); }

    @Override
    protected ArrayList<Savings> createObjects(ResultSet resultSet) {
        try{
            while (resultSet.next())
            {
                int id = resultSet.getInt("id_savings");
                int idUser = resultSet.getInt("id_user");
                double initialDeposit = resultSet.getInt("balance");
                // select the account holder with "idUser"
                AccHolder accHolder = selectById(idUser);
                Savings savingsAcc = new Savings(id, accHolder, initialDeposit);
                savingAccounts.add(savingsAcc);
            }
            return savingAccounts;
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}

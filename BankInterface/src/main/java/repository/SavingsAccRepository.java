package repository;

import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

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
                int id_category = resultSet.getInt("id_category");
                double initialDeposit = resultSet.getInt("balance");
                // select the account holder with "idUser"
                CategoryRepository categoryRepository = new CategoryRepository();
                Category category = (Category) categoryRepository.selectById(id_category);
                AccHolderRepository accHolderRepository = new AccHolderRepository();
                AccHolder accHolder = (AccHolder) accHolderRepository.selectById(idUser);
                Savings savingsAcc = new Savings(id, accHolder, initialDeposit, category);
                savingAccounts.add(savingsAcc);
            }
            return savingAccounts;
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    protected Savings createObject(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                int id = resultSet.getInt("id_savings");
                double balance = resultSet.getDouble("balance");
                int id_user = resultSet.getInt("id_user");
                int id_category = resultSet.getInt("id_category");
                CategoryRepository categoryRepository = new CategoryRepository();
                Category category = (Category) categoryRepository.selectById(id_category);
                AccHolderRepository accHolderRepository = new AccHolderRepository();
                AccHolder accHolder = (AccHolder) accHolderRepository.selectById(id_user);
                Savings savings = new Savings(id, accHolder, balance, category);
                return savings;
            }
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}

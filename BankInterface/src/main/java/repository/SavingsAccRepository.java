package repository;

import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

public class SavingsAccRepository extends Repository {

    private ArrayList<Savings> savingAccounts;

    /**
     * / Constructor used to send the name of the corresponding table from db
     */
    public SavingsAccRepository() { super("savingsacc"); }

    @Override
    protected ArrayList<Savings> createObjects(ResultSet resultSet) {
        savingAccounts = new ArrayList<>();
        try{
            while (resultSet.next())
            {
                int id = resultSet.getInt("id_savings");
                int idUser = resultSet.getInt("id_user");
                int id_category = resultSet.getInt("id_category");
                double initialDeposit = resultSet.getInt("balance");
                int safetyBoxId = resultSet.getInt("safetybox_id");
                int safetyBoxKey = resultSet.getInt("safetybox_key");
                // select the account holder with "idUser"
                CategoryRepository categoryRepository = new CategoryRepository();
                Category category = (Category) categoryRepository.selectById(id_category);
                AccHolderRepository accHolderRepository = new AccHolderRepository();
                AccHolder accHolder = (AccHolder) accHolderRepository.selectById(idUser);
                Savings savingsAcc = new Savings(id, accHolder, initialDeposit, safetyBoxId, safetyBoxKey, category);
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
                int safetyBoxId = resultSet.getInt("safetybox_id");
                int safetyBoxKey = resultSet.getInt("safetybox_key");
                CategoryRepository categoryRepository = new CategoryRepository();
                Category category = (Category) categoryRepository.selectById(id_category);
                AccHolderRepository accHolderRepository = new AccHolderRepository();
                AccHolder accHolder = (AccHolder) accHolderRepository.selectById(id_user);
                Savings savings = new Savings(id, accHolder, balance, safetyBoxId, safetyBoxKey, category);
                return savings;
            }
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Savings selectByAccountNr(String accNumber) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM ";
        query = query + "account WHERE account_nr = \'" + accNumber + "\'";

        try {
            connection = DbConnection.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObject(resultSet);
        }catch(SQLException e) {
            LOGGER.log(Level.WARNING, "savingsacc SavingsAccRepository:selectByAccountNr " + e.getMessage());
            return null;
        }finally{
            DbConnection.close(resultSet);
            DbConnection.close(statement);
            DbConnection.close(connection);
        }
    }
}

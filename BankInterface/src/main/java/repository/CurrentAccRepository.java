package repository;

import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

public class CurrentAccRepository extends Repository{
    private ArrayList<Current> currentAccounts;

    /**
     * / Constructor used to send the name of the corresponding table from db
     */
    public CurrentAccRepository() { super("currentacc"); }

    @Override
    protected ArrayList<Current> createObjects(ResultSet resultSet) {
        currentAccounts = new ArrayList<>();
        try{
            while (resultSet.next())
            {
                int id = resultSet.getInt("id_current");
                int idUser = resultSet.getInt("id_user");
                int id_category = resultSet.getInt("id_category");
                double initialDeposit = resultSet.getInt("balance");
                int cardId = resultSet.getInt("debitcard_nr");
                int cardPin = resultSet.getInt("debitcard_pin");
                // select the account holder with "idUser"
                CategoryRepository categoryRepository = new CategoryRepository();
                Category category = (Category) categoryRepository.selectById(id_category);
                AccHolderRepository accHolderRepository = new AccHolderRepository();
                AccHolder accHolder = (AccHolder) accHolderRepository.selectById(idUser);
                Current currentAcc = new Current(id, accHolder, initialDeposit, cardId, cardPin, category);
                currentAccounts.add(currentAcc);
            }
            return currentAccounts;
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    protected Current createObject(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                int id = resultSet.getInt("id_current");
                int id_category = resultSet.getInt("id_category");
                double balance = resultSet.getDouble("balance");
                int id_user = resultSet.getInt("id_user");
                int cardId = resultSet.getInt("debitcard_nr");
                int cardPin = resultSet.getInt("debitcard_pin");
                AccHolderRepository accHolderRepository = new AccHolderRepository();
                AccHolder accHolder = (AccHolder) accHolderRepository.selectById(id_user);
                CategoryRepository categoryRepository = new CategoryRepository();
                Category category = (Category) categoryRepository.selectById(id_category);
                Current current = new Current(id, accHolder, balance, cardId, cardPin, category);
                return current;
            }
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Current selectByAccountNr(String accNumber) {
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
            LOGGER.log(Level.WARNING, "currentacc CurrentAccRepository:selectByAccountNr " + e.getMessage());
            return null;
        }finally{
            DbConnection.close(resultSet);
            DbConnection.close(statement);
            DbConnection.close(connection);
        }
    }
}

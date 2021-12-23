package repository;

import model.AccHolder;
import model.Account;
import model.Transaction;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

public class AccHolderRepository extends Repository {

    private ArrayList<AccHolder> accHolders = new ArrayList<>();

    public AccHolderRepository() {
        super("accholder");
    }

    @Override
    protected ArrayList<AccHolder> createObjects(ResultSet resultSet) {
        try{
            while (resultSet.next())
            {
                int id = resultSet.getInt("id_user");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String cnp = resultSet.getString("cnp");
                String userName = resultSet.getString("user_name");
                String password = resultSet.getString("password");
                AccHolder accHolder = new AccHolder(id, firstName, lastName, cnp, userName, password);
                accHolders.add(accHolder);
            }
            return accHolders;
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    protected AccHolder createObject(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                int id = resultSet.getInt("id_user");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String cnp = resultSet.getString("cnp");
                String userName = resultSet.getString("user_name");
                String password = resultSet.getString("password");
                AccHolder accHolder = new AccHolder(id, firstName, lastName, cnp, userName, password);
                return accHolder;
            }
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Method used for registration and sign in
     * @param username the userame provied by the user
     * @param password user's password
     * @return true if the user already exists in the database, false otherwise
     */
    public boolean checkIfUserExists(String username, String password) {
        String  query = "SELECT COUNT (*) AS counter FROM accholder WHERE user_name = \'" + username + "\' "
                    + "AND password = \'" + password + "\'";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DbConnection.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int count = 0;
        try {
            if(resultSet.next()){
                count = resultSet.getInt("counter");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            DbConnection.close(resultSet);
            DbConnection.close(statement);
            DbConnection.close(connection);
        }

        if(count != 0){
            return true;
        }
        return false;
    }

    public AccHolder selectByUsername(String username){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM view_accholder WHERE user_name = \'" +  username + "\'";
        try {
            connection = DbConnection.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObject(resultSet);
        }catch(SQLException e){
            LOGGER.log(Level.WARNING, " Repository:selectById " + e.getMessage());
            return null;
        }finally{
            DbConnection.close(resultSet);
            DbConnection.close(statement);
            DbConnection.close(connection);
        }
    }
}

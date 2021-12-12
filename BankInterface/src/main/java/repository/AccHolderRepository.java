package repository;

import model.AccHolder;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccHolderRepository extends Repository {

    private ArrayList<AccHolder> accHolders = new ArrayList<>();

    public AccHolderRepository() {
        super("accholder");
    }

    /**
     * Method that checks if the user already exists in the db
     * @param user: the user that we want to add in the database
     * @return a boolean value which indicates that the user exists in the db if true
     */
    public boolean checkIfUserAlreadyRegistered(User user) {
        String query = "SELECT COUNT(*) AS counter FROM view_accholder WHERE id_user = " + user.id;
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
}

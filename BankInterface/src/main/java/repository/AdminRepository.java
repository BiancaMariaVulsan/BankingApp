package repository;

import model.AccHolder;
import model.Admin;
import model.User;

import java.sql.*;
import java.util.ArrayList;

public class AdminRepository extends Repository{

    private ArrayList<Admin> admins = new ArrayList<>();

    public AdminRepository() {
        super("admin");
    }

    /**
     * Method that checks if the user already exists in the db
     * @param user: the user that we want to add in the database
     * @return a boolean value which indicates that the user exists in the db if true
     */
    public boolean checkIfUserAlreadyRegistered(User user) {
        String query = "SELECT COUNT(*) AS counter FROM view_admin WHERE id_user = " + user.id;
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
    protected ArrayList<Admin> createObjects(ResultSet resultSet) {
        try{
            while (resultSet.next())
            {
                int id = resultSet.getInt("id_user");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                Date birthdate = resultSet.getDate("birthdate");
                Admin admin = new Admin(id, firstName, lastName, birthdate);
                admins.add(admin);
            }
            return admins;
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}

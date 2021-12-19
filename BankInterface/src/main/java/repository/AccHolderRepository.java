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
}

package repository;

import model.AccHolder;
import model.Admin;
import model.BaseModel;
import model.User;

import java.sql.*;
import java.util.ArrayList;

public class AdminRepository extends Repository{

    private ArrayList<Admin> admins = new ArrayList<>();

    public AdminRepository() {
        super("admin");
    }

    @Override
    protected ArrayList<Admin> createObjects(ResultSet resultSet) {
        try{
            while (resultSet.next())
            {
                int id = resultSet.getInt("id_user");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String cnp = resultSet.getString("cnp");
                Date birthdate = resultSet.getDate("birthdate");
                Admin admin = new Admin(id, firstName, lastName, birthdate, cnp);
                admins.add(admin);
            }
            return admins;
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    protected Admin createObject(ResultSet resultSet) {
        return null;
    }
}

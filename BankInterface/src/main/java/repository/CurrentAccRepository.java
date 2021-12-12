package repository;

import model.AccHolder;
import model.Current;
import model.Savings;

import java.sql.ResultSet;
import java.util.ArrayList;

public class CurrentAccRepository extends Repository{
    private ArrayList<Current> currentAccounts = new ArrayList<>();

    /**
     * / Constructor used to send the name of the corresponding table from db
     */
    public CurrentAccRepository() { super("currentacc"); }

    @Override
    protected ArrayList<Current> createObjects(ResultSet resultSet) {
        try{
            while (resultSet.next())
            {
                int id = resultSet.getInt("id_current");
                int idUser = resultSet.getInt("id_user");
                double initialDeposit = resultSet.getInt("balance");
                // select the account holder with "idUser"
                AccHolder accHolder = selectById(idUser);
                Current currentAcc = new Current(id, accHolder, initialDeposit);
                currentAccounts.add(currentAcc);
            }
            return currentAccounts;
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}

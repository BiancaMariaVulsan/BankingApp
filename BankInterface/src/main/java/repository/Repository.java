package repository;

import model.*;

import javax.swing.*;
import java.lang.reflect.Field;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Repository<T extends BaseModel> {

    protected static final Logger LOGGER = Logger.getLogger(DbConnection.class.getName());
    private final String tableName;

    /**
     * Constructor of the class, sets the type of the object received as generic
     */
    public Repository(String tableName){
        this.tableName = tableName;
    }

    /**
     * Method handling the SELECT (*) SQL command
     * @return returns the SELECT result as a List of objects of the T (generic) type
     */
    public ArrayList<T> selectAllEntities() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query;
        if(tableName.equals("transaction") || tableName.equals("category")) {
            query = "SELECT * FROM " + tableName;
        } else {
            query = "SELECT * FROM view_" + tableName;
        }

        try{
            connection = DbConnection.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);
        }catch(SQLException e){
            LOGGER.log(Level.WARNING, tableName + " Repository:selectAllEntities " + e.getMessage());
            return null;
        }finally{
            DbConnection.close(resultSet);
            DbConnection.close(statement);
            DbConnection.close(connection);
        }
    }

    /**
     * Method handling the SELECT by id SQL command
     * @return returns the object with te specified id
     */
    public T selectById(int id){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM ";
        if(tableName.equals("accholder") || tableName.equals("admin")) {
             query = query + "view_" + tableName + " WHERE id_user = " + id;
        } else if(tableName.equals("currentacc")) {
            query = query + "view_" + tableName + " WHERE id_current = " + id;
        } else if(tableName.equals("savingsacc")) {
            query = query + "view_" + tableName + " WHERE id_savings = " + id;
        } else if(tableName.equals("category")) {
            query = query + tableName + " WHERE id_category = " + id;
        } else if(tableName.equals("transaction")) {
            query = query + tableName + " WHERE id_transaction = " + id;
        } else {
            LOGGER.log(Level.WARNING, tableName + " Repository:selectById " + "Invalid name for the table");
            return null;
        }
        try {
        connection = DbConnection.getConnection();
        statement = connection.prepareStatement(query);
        resultSet = statement.executeQuery();

        return createObject(resultSet);
        }catch(SQLException e){
            LOGGER.log(Level.WARNING, tableName + " Repository:selectAllEntities " + e.getMessage());
            return null;
        }finally{
            DbConnection.close(resultSet);
            DbConnection.close(statement);
            DbConnection.close(connection);
        }
    }

    /**
     * Method handling the DELETE SQL command
     * @param obj represents the object that needs to be deleted from the database
     */
    public void deleteEntity(T obj){
        Connection connection = null;
        PreparedStatement statement = null;
        String query = "DELETE FROM " + tableName + " WHERE ";
        String baseQuery = "DELETE FROM ";
        if(obj instanceof AccHolder) {
            query = query + "id_user";
            baseQuery = baseQuery + "person WHERE id";
        } else if(obj instanceof Admin) {
            query = query + "id_user";
            baseQuery = baseQuery + "person WHERE id";
        } else if(obj instanceof Transaction) {
            query = query + "id_transaction";
        } else if(obj instanceof Savings) {
            query = query + "id_savings";
            baseQuery = baseQuery + "account WHERE id_account";
        } else if(obj instanceof Current) {
            query = query + "id_current";
            baseQuery = baseQuery + "account WHERE id_account";
        } else {
            System.out.println("Invalid type of the object to delete");
        }
        // get the id of the object from db
        query = query + " = " + obj.id;
        baseQuery = baseQuery + " = " + obj.id;
        try {
            connection = DbConnection.getConnection();
            statement = connection.prepareStatement(query);
            statement.executeUpdate();
            statement = connection.prepareStatement(baseQuery);
            statement.executeUpdate();
        } catch (SecurityException e1) {
            e1.printStackTrace();
        } catch(SQLException e){
            LOGGER.log(Level.WARNING, tableName + " Repository:deleteEntity " + e.getMessage());
        }finally{
            DbConnection.close(statement);
            DbConnection.close(connection);
        }
    }

    private int getLastIdInserted(String tableName) {
        Connection connection = null;
        PreparedStatement statement = null;
        String queryId = "";
        if(tableName.equals("person")) {
            queryId = "SELECT max(id) as maxId FROM " + tableName;
        }else if(tableName.equals("account")) {
            queryId = "SELECT max(id_account) as maxId FROM " + tableName;
        } else {
            LOGGER.log(Level.WARNING, tableName + " Repository:getLastIdInserted " + "The table name is not valid!");
            return -1;
        }
        int lastId = 0;
        try {
            connection = DbConnection.getConnection();
            statement = connection.prepareStatement(queryId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                lastId = resultSet.getInt("maxId");
                return lastId;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Method handling the INSERT SQL command
     * @param obj represents the object that needs to be inserted into the database
     */
    public void insertEntity(T obj) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = "INSERT INTO " + tableName;
        String baseQuery = "";
        try {
            connection = DbConnection.getConnection();

            if (obj instanceof AccHolder) {
                baseQuery = "INSERT INTO person (first_name, last_name, cnp) VALUES (" +
                        "\'" + ((AccHolder) obj).getFirstName() + "\', \'" + ((AccHolder) obj).getLastName() + "\', \'" + ((AccHolder) obj).getCnp() + "\')";
                // insert into person
                try {
                    statement = connection.prepareStatement(baseQuery);
                    statement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                // get the id of the user from person table
                int idPerson = getLastIdInserted("person");

                // prepare the query to insert into accholder with the same id as in person
                query = query + "(id_user, user_name, password) VALUES (" +
                        idPerson + ", \'"  + ((AccHolder) obj).getUserName() + "\', \'" + ((AccHolder) obj).getPassword() + "\')";
            } else if (obj instanceof Admin) {
                baseQuery = "INSERT INTO person (first_name, last_name, cnp) VALUES (" +
                        "\'" + ((Admin) obj).getFirstName() + "\', \'" + ((Admin) obj).getLastName() + "\', \'" + ((Admin) obj).getCnp() + "\')";
                // insert into person
                try {
                    statement = connection.prepareStatement(baseQuery);
                    statement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                // get the id of the user from person table
                int idPerson = getLastIdInserted("person");

                // prepare the query to insert into admin with the same id as in person
                query = query + "(id_user, birthdate) VALUES (" +
                        idPerson + ", \'" + ((Admin) obj).getDate().toString() + "\')";

            } else if (obj instanceof Transaction) {
                query = query + "(value, id_sender, id_receiver, date) VALUES (" +
                        ((Transaction) obj).getValue() + ", " + ((Transaction) obj).getSender().id + ", " + ((Transaction) obj).getReceiver().id +
                        ", \'" + ((Transaction) obj).getDate().toString() + "\')";

            } else if (obj instanceof Savings) {
                baseQuery = "INSERT INTO account (balance, account_nr, id_user, id_category) VALUES (" +
                        ((Savings) obj).getBalance() + ", \'" + ((Savings) obj).getAccNumber() +
                        "\', " + ((Savings) obj).getAccHolder().id + ", " + ((Savings) obj).getCategory().id + ")";
                // insert into account
                try {
                    statement = connection.prepareStatement(baseQuery);
                    statement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                // get the last id inserted into account
                int accountId = getLastIdInserted("account");

                // insert into savings
                query = query + "(id_savings, safetybox_id, safetybox_key) VALUES (" +
                        accountId + ", " + ((Savings) obj).getSafetyDepositBoxId() + ", " + ((Savings) obj).getSafetyDepositBoxKey() + ")";

            } else if (obj instanceof Current) {
                baseQuery = "INSERT INTO account (balance, account_nr, id_user, id_category) VALUES (" +
                        ((Current) obj).getBalance() + ", \'" + ((Current) obj).getAccNumber() +
                        "\', " + ((Current) obj).getAccHolder().id + ", " + ((Current) obj).getCategory().id + ")";
                // insert into account
                try {
                    statement = connection.prepareStatement(baseQuery);
                    statement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                // get the last id inserted into account
                int accountId = getLastIdInserted("account");

                // insert into current
                query = query + "(id_current, debitcard_nr, debitcard_pin) VALUES (" +
                        accountId + ", " + ((Current) obj).getDebitCardNr() + ", " + ((Current) obj).getDebitCardPIN() + ")";
            } else {
                System.out.println("Invalid type of the object to insert");
            }
            try {
                statement = connection.prepareStatement(query);
                statement.executeUpdate();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, tableName + " Repository:insertEntity " + e.getMessage());
            } finally {
                DbConnection.close(statement);
                // DbConnection.close(connection);
            }
        } finally {
            DbConnection.close(connection);
        }
    }

    /**
     * Method that checks if the user already exists in the db
     * @param obj: the object that we want to add in the database
     * @return a boolean value which indicates that the user exists in the db if true
     */
    public boolean checkIfObjectAlreadyExists(Object obj) {
        String query = "";
        if(obj instanceof User) {
            query = "SELECT COUNT (*) AS counter FROM person WHERE cnp = \'" + ((User) obj).getCnp() + "\'";
        } else if(obj instanceof Account) {
            query = "SELECT COUNT (*) AS counter FROM account WHERE account_nr = \'" + ((Account) obj).getAccNumber() + "\'";
        } else {
            LOGGER.log(Level.WARNING, tableName + " Repository:checkIfObjectAlreadyExists " + "The object type is not valid!");
            return false;
        }

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

    /**
     * Method handling the UPDATE SQL command
     * @param obj represents the object that needs to be updated in the database
     * @param columnName represents the name of the column to be updated
     * @param value represents the value to be inserted in the specific column
     */
    public void updateEntity(T obj, String columnName, double value){ // TODO: USE Field instead of String and double
        Connection connection = null;
        PreparedStatement statement = null;
        String query = "UPDATE ";
        if(obj instanceof Account) {
            query = query + "account SET " + columnName + " = " + value + " WHERE id_account = " + obj.id;
        } else {
            LOGGER.log(Level.WARNING, tableName + " Repository:updateEntity " + "Update method not defined for the object given!");
        }
        try{
            connection = DbConnection.getConnection();
            statement = connection.prepareStatement(query);
            statement.executeUpdate();
        }catch(SQLException e){
            LOGGER.log(Level.WARNING, tableName + " Repository:updateEntity " + e.getMessage());
        }finally{
            DbConnection.close(statement);
            DbConnection.close(connection);
        }
    }

    public ArrayList<T> selectByUserId(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM ";
        if(tableName.equals("transaction")) {
            query = query + "transaction WHERE id_sender = " + id + " OR id_receiver = " + id;
        } else if(tableName.equals("currentacc")) {
            query = query + "view_currentacc WHERE id_user = " + id;
        } else if(tableName.equals("savingsacc")) {
            query = query + "view_savingsacc WHERE id_user = " + id;
        } else {
            System.out.println("Invalid name of the table!");
        }

        try {
            connection = DbConnection.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);
        }catch(SQLException e) {
            LOGGER.log(Level.WARNING, "currentacc Repository:selectByUserId " + e.getMessage());
            return null;
        }finally{
            DbConnection.close(resultSet);
            DbConnection.close(statement);
            DbConnection.close(connection);
        }
    }

    /**
     * Method that uses the result set generated from the created query to create the required object
     * @param resultSet represents the result of the created and run query
     * @return a list with the created objects of type given by the generic of the class
     */
    protected abstract ArrayList<T> createObjects(ResultSet resultSet);

    /**
     * Method that uses the result set generated from the created query to create the required object
     * @param resultSet represents the result of the created and run query
     * @return an object of type given by the generic of the class
     */
    protected abstract T createObject(ResultSet resultSet);
}

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
    public ArrayList<T> selectAllEntities(){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query;
        if(tableName.equals("transaction")) {
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
     * @return returns the the object with te specified id
     */
    public AccHolder selectById(int id){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM view_accholder WHERE id_user = " + id; // TODO: make a case statement and replace id_user or move the method
        try {
            connection = DbConnection.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            // create account holder
            if (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String cnp = resultSet.getString("cnp");
                String userName = resultSet.getString("user_name");
                String password = resultSet.getString("password");
                AccHolder accHolder = new AccHolder(id, firstName, lastName, cnp, userName, password);
                return accHolder;
            }
            else return null;

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
        } else if(obj instanceof Current) {
            query = query + "id_current";
        } else {
            System.out.println("Invalid type of the object to insert");
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
                baseQuery = "INSERT INTO person (first_name, last_name) VALUES (" +
                        "\'" + ((AccHolder) obj).getFirstName() + "\', \'" + ((AccHolder) obj).getLastName() + "\')";
                // insert into person
                try {
                    statement = connection.prepareStatement(baseQuery);
                    statement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                // get the id of the user from person table
                String queryId = "SELECT max(id) as maxId FROM person";
                int idPerson = 0;
                try {
                    statement = connection.prepareStatement(queryId);
                    ResultSet resultSet = statement.executeQuery();
                    if(resultSet.next()) {
                        idPerson = resultSet.getInt("maxId");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                query = query + "(id_user, cnp, user_name, password) VALUES (" +
                        idPerson + ", \'" + ((AccHolder) obj).getCnp() + "\', \'" + ((AccHolder) obj).getUserName() + "\', \'" + ((AccHolder) obj).getPassword() + "\')";
            } else if (obj instanceof Admin) {
                query = query + "(id_user, birthdate) VALUES (" +
                        obj.id + ", \'" + ((Admin) obj).getDate().toString() + "\')";
                baseQuery = "INSERT INTO person (id, first_name, last_name) VALUES (" +
                        obj.id + ", \'" + ((Admin) obj).getFirstName() + "\', \'" + ((Admin) obj).getLastName() + "\')";
            } else if (obj instanceof Transaction) {

            } else if (obj instanceof Savings) {
                query = query + "(id_savings, rate, balance, account_nr, id_user) VALUES (" +
                        obj.id + ", \'" + ((Savings) obj).getRate() + "\', \'" + ((Savings) obj).getBalance() + "\', \'" +
                        ((Savings) obj).getAccNumber() + "\', \'" + ((Savings) obj).getAccHolder().id + "\')";
            } else if (obj instanceof Current) {
                query = query + "(id_current, id_user, balance, account_nr, rate) VALUES (" +
                        obj.id + ", \'" + ((Current) obj).getAccHolder().id + "\', \'" + ((Current) obj).getBalance() + "\', \'" +
                        ((Current) obj).getAccNumber() + "\', \'" + ((Current) obj).getRate() + "\')";
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
     * Method handling the UPDATE SQL command
     * @param obj represents the object that needs to be updated in the database
     */
//    public void updateEntity(T obj, String query){
//        Connection connection = null;
//        PreparedStatement statement = null;
//        try{
//            connection = DbConnection.getConnection();
//            statement = connection.prepareStatement(query);
//            statement.executeUpdate();
//        }catch(SQLException e){
//            LOGGER.log(Level.WARNING, tableName + " Repository:updateEntity " + e.getMessage());
//        }finally{
//            DbConnection.close(statement);
//            DbConnection.close(connection);
//        }
//    }

    /**
     * Method that uses the result set generated from the created query to create the required objects through reflection
     * @param resultSet represents the result of the created and run query
     * @return a list with the created objects of type given by the generic of the class
     */
    protected abstract ArrayList<T> createObjects(ResultSet resultSet);
}

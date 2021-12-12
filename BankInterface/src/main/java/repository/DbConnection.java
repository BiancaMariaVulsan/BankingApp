package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbConnection {

    private static final String url = "jdbc:postgresql://localhost:5432/bank";
    private static DbConnection singleInstance = new DbConnection();

    /**
     * Method establishes the database connection
     */
    private Connection connect() {
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "Vulsan2001");
        try {
            Connection conn = DriverManager.getConnection(url, props);
            System.out.println("Connected to database");
            // conn.close();
            return conn;
        } catch (SQLException e) {
            System.out.println("Error: database connection failed");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method that creates the connection to the database using the provided values from the static fields of the class
     * @return a Connection object representing the database connection
     */
    public static Connection getConnection(){
        return singleInstance.connect();
    }

    /**
     * Method that closes the provided connection
     * @param connection: a Connection object associated to the connection that needs to be closed
     */
    public static void close(Connection connection){
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Closing database connection" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Method that closes the provided statement
     * @param statement: a Statement object associated to the statement that needs to be closed
     */
    public static void close(Statement statement){
        try {
            statement.close();
        } catch (SQLException e) {
            System.out.println("Closing database connection"  + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Method that closes the provided result set
     * @param result: a ResultSet object associated to the result set that needs to be closed
     */
    public static void close(ResultSet result){
        try {
            result.close();
        } catch (SQLException e) {
            System.out.println("Closing database connection" + e.getMessage());
            e.printStackTrace();
        }
    }
}

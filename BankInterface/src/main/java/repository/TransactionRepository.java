package repository;

import model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;

public class TransactionRepository extends Repository {

    private ArrayList<Transaction> transactions;

    public TransactionRepository() {
        super("transaction");
    }

    @Override
    protected ArrayList<Transaction> createObjects(ResultSet resultSet) {
        transactions = new ArrayList<>();
        try{
            while (resultSet.next())
            {
                int id = resultSet.getInt("id_transaction");
                int id_sender = resultSet.getInt("id_sender");
                int id_receiver = resultSet.getInt("id_receiver");
                double value = resultSet.getDouble("value");
                Date date = resultSet.getDate("date");
                // int id_category = resultSet.getInt("id_category");
                SavingsAccRepository savingsAccRepository = new SavingsAccRepository();
                CurrentAccRepository currentAccRepository = new CurrentAccRepository();
                Account senderAccount = (Account) savingsAccRepository.selectById(id_sender); // TODO: CHECK IF IT WORKS
                if(senderAccount == null) {
                    senderAccount = (Account) currentAccRepository.selectById(id_sender);
                }
                Account receiverAccount = (Account) savingsAccRepository.selectById(id_receiver); // TODO: CHECK IF IT WORKS
                if(receiverAccount == null) {
                    receiverAccount = (Account) currentAccRepository.selectById(id_receiver);
                }
                Transaction transaction = new Transaction(id, senderAccount, receiverAccount, value, date, receiverAccount.getCategory());
                transactions.add(transaction);
            }
            return transactions;
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    protected Transaction createObject(ResultSet resultSet) {
        return null;
    }

    public ArrayList<Transaction> selectByUserId(int id){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM transaction WHERE id_sender = " + id; // + " OR id_receiver = " + id;
        try {
            connection = DbConnection.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);
        }catch(SQLException e){
            LOGGER.log(Level.WARNING, "transaction Repository:selectByUserId " + e.getMessage());
            return null;
        }finally{
            DbConnection.close(resultSet);
            DbConnection.close(statement);
            DbConnection.close(connection);
        }
    }
}

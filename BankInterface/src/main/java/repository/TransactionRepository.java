package repository;

import model.AccHolder;
import model.Transaction;
import model.User;

import java.sql.ResultSet;
import java.util.ArrayList;

public class TransactionRepository extends Repository {

    private ArrayList<Transaction> transactions = new ArrayList<>();

    public TransactionRepository() {
        super("transaction");
    }

    @Override
    protected ArrayList<Transaction> createObjects(ResultSet resultSet) {
        try{
            while (resultSet.next())
            {
                int id = resultSet.getInt("id_transaction");
                Transaction transaction = new Transaction(id);
                transactions.add(transaction);
            }
            return transactions;
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}

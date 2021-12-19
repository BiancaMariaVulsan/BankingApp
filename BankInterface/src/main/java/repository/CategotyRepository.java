package repository;

import model.AccHolder;
import model.BaseModel;
import model.Category;
import model.Transaction;

import java.sql.ResultSet;
import java.util.ArrayList;

public class CategotyRepository extends Repository {
    private ArrayList<Category> categories = new ArrayList<>();

    public CategotyRepository() {
        super("categoty");
    }

    @Override
    protected ArrayList<Category> createObjects(ResultSet resultSet) {
        try{
            while (resultSet.next())
            {
                int id = resultSet.getInt("id_category");
                String categoryName = resultSet.getString("category");
                Category category = new Category(id, categoryName);
                categories.add(category);
            }
            return categories;
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    protected Category createObject(ResultSet resultSet) {
        return null;
    }
}

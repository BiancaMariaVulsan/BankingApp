package repository;

import model.*;

import java.sql.ResultSet;
import java.util.ArrayList;

public class CategoryRepository extends Repository {
    private ArrayList<Category> categories = new ArrayList<>();

    public CategoryRepository() {
        super("category");
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

        try {
            if (resultSet.next()) {
                int id = resultSet.getInt("id_category");
                String categoryName = resultSet.getString("category");
                Category category = new Category(id, categoryName);
                return category;
            }
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}

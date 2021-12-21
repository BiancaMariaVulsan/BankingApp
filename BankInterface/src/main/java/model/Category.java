package model;

public class Category extends BaseModel{

    private String name;

    public Category(int id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

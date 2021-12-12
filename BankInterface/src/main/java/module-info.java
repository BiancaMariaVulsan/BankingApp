module com.example.banking {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens view to javafx.fxml;
    exports view;
}
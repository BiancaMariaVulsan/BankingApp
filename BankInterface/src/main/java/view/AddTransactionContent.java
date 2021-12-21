package view;

import controller.Controller;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ConcurrentModificationException;
import java.util.ResourceBundle;

public class AddTransactionContent implements Initializable {
    Controller controller;

    public AddTransactionContent(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
// get the accounts of this user to populate the list
        // get from the fields, validate and add transaction on handle of button add
    }
}

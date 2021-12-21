package view;

import controller.Controller;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class DisplayTransactionsContent implements Initializable {
    Controller controller;

    public DisplayTransactionsContent(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
// get the list of all transactions of this user, populate the table
    }
}

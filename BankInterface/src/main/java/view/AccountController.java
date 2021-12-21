package view;

import controller.Controller;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class AccountController implements Initializable {
    Controller controller;

    public AccountController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    // in constructor get the account data and set the 4 text fields
    // we get from the spinner box the amount to withdraw/deposit, validate and then apply on the respective account
    }
}

package view;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;

import java.io.Console;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddAccountContent implements Initializable {
    Controller controller;
    @FXML
    private ChoiceBox<String> accountTypesChoiceBox;

    public AddAccountContent(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        accountTypesChoiceBox.getItems().add("Current");
        accountTypesChoiceBox.getItems().add("Savings");
        // get from the field the initial deposit and then create based on choice box and add an account
    }
}

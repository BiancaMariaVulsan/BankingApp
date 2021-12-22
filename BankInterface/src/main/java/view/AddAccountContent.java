package view;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import model.AccHolder;

import java.io.Console;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddAccountContent implements Initializable {
    @FXML
    private CheckBox termsCheckBox;
    @FXML
    private TextField initialDepositTextField;
    @FXML
    private Button addButton;
    AccHolder accHolder;
    Controller controller;

    @FXML
    private ChoiceBox<String> accountTypesChoiceBox;

    public AddAccountContent(AccHolder accHolder, Controller controller) {
        this.accHolder = accHolder;
        this.controller = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addButton.setDisable(true);
        termsCheckBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (termsCheckBox.isSelected()) {
                    addButton.setDisable(false);
                } else {
                    addButton.setDisable(true);
                }
            }
        });
        accountTypesChoiceBox.getItems().add("Current");
        accountTypesChoiceBox.getItems().add("Savings");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {

                    if (accountTypesChoiceBox.getSelectionModel().getSelectedItem().equals("Current")) {
                        controller.addAccountCurrent(Double.parseDouble(initialDepositTextField.getText()), accHolder);
                    } else {
                        controller.addAccountSavings(Double.parseDouble(initialDepositTextField.getText()), accHolder);
                    }
                } catch (RuntimeException exception) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(exception.getMessage());
                    Button confirm = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                    confirm.setDefaultButton(false);
                    confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
                    alert.showAndWait();
                    return;
                }
            }
        });

    }
}

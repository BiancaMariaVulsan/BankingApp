package view;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.AccHolder;
import model.Account;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddUserTransactionController implements Initializable {
    @FXML
    private Button exitButton;
    @FXML
    private Button addButton;
    @FXML
    private TextField recieverTextField;
    @FXML
    private TextField amountTextField;
    @FXML
    private TextArea descriptionTextField;
    Controller controller;
    AccHolder accHolder;
    Account account;

    public AddUserTransactionController(Controller controller, AccHolder accHolder, Account account) {
        this.controller = controller;
        this.accHolder = accHolder;
        this.account = account;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    controller.addTransaction(account,recieverTextField.getText(),Double.parseDouble(amountTextField.getText()),descriptionTextField.getText());
                }catch (RuntimeException exception){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(exception.getMessage());
                    Button confirm = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                    confirm.setDefaultButton(false);
                    confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
                    alert.showAndWait();
                }
            }
        });
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Exit");
                alert.setContentText("Are you sure you want to exit?");
                Button confirm = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                confirm.setDefaultButton(false);
                confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK) {
                    Stage stage = (Stage) addButton.getScene().getWindow();
                    stage.close();
                }
            }
        });
    }
}

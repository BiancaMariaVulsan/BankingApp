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
import model.Current;
import model.Savings;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AccountController implements Initializable {
    @FXML
    private Button exitButton;
    @FXML
    private Spinner amountSpinner;
    @FXML
    private Button withdrawButton;
    @FXML
    private Button depositButton;
    @FXML
    private TextField accNumberTextField;
    @FXML
    private TextField cardNumberTextField;
    @FXML
    private TextField balanceTextField;
    @FXML
    private TextField rateTextField;
    Controller controller;
    AccHolder accHolder;
    Account account;

    public AccountController(Controller controller) {
        this.controller = controller;
    }

    public AccountController(Controller controller, AccHolder accHolder, Account account) {
        this.controller = controller;
        this.accHolder = accHolder;
        this.account = account;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        accNumberTextField.setText(account.getAccNumber());
        balanceTextField.setText(String.valueOf(account.getBalance()));
        rateTextField.setText(String.valueOf(account.getRate()));
        if (account instanceof Savings) {
            Savings savings = (Savings) account;
            cardNumberTextField.setText(String.valueOf(savings.getSafetyDepositBoxId()));
        } else {
            Current current = (Current) account;
            cardNumberTextField.setText(String.valueOf(current.getDebitCardNr()));
        }

        double amount = Integer.parseInt((String) amountSpinner.getValue());
        withdrawButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controller.makeWithdrawal(account,amount);
            }
        });
        depositButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controller.makeDeposit(account,amount);
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
                    Stage stage = (Stage) accNumberTextField.getScene().getWindow();
                    stage.close();
                }
            }
        });
    }
}

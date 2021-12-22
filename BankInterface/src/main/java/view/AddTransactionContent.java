package view;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.AccHolder;
import model.Account;

import java.net.URL;
import java.util.ConcurrentModificationException;
import java.util.ResourceBundle;

public class AddTransactionContent implements Initializable {
    @FXML
    private Button addButton;
    @FXML
    private TextField recieverTextField;
    @FXML
    private TextField amountTextField;
    @FXML
    private TextArea descriptionTextField;
    @FXML
    private ListView<Account> accountsListView;
    private ObservableList<Account> accountsItems = FXCollections.observableArrayList();
    AccHolder accHolder;
    Controller controller;

    public AddTransactionContent(AccHolder accHolder, Controller controller) {
        this.accHolder = accHolder;
        this.controller = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setAccountsListView();
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    controller.addTransaction(accountsListView.getSelectionModel().getSelectedItem(), recieverTextField.getText(), Double.parseDouble(amountTextField.getText()), descriptionTextField.getText());
                    setAccountsListView();
                } catch (RuntimeException exception) {
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

    }

    public void setAccountsListView() {
        accountsItems.clear();
        accountsItems.addAll(controller.getAccountsByUser(accHolder));
        accountsListView.setItems(accountsItems);
    }
}

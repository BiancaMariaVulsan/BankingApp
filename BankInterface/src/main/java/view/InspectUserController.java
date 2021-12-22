package view;

import controller.Controller;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.AccHolder;
import model.Account;
import model.Transaction;

import java.io.Console;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InspectUserController implements Initializable {
    @FXML
    private TableView<Transaction> transactionsTableView;
    ObservableList<Transaction> transactionsItems = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Transaction,String> recieverColumn;
    @FXML
    private TableColumn<Transaction,String> categoryColumn;
    @FXML
    private TableColumn<Transaction,String> valueColumn;
    @FXML
    private TableColumn<Transaction,String> dateColumn;
    @FXML
    private ChoiceBox<Account> accountChoiceBox;
    @FXML
    private ChoiceBox<String> accountTypeChoiceBox;
    @FXML
    private Button removeButton;
    @FXML
    private Button addButton;
    @FXML
    private Spinner<Integer> initialDepositSpinner;
    Controller controller;
    @FXML
    private Button addTransactionButton;
    AccHolder accHolder;

    public InspectUserController(AccHolder accHolder,Controller controller) {
        this.controller = controller;
        this.accHolder = accHolder;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        accountTypeChoiceBox.getItems().add("Savings");
        accountTypeChoiceBox.getItems().add("Current");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    if (accountTypeChoiceBox.getValue().equals("Savings")){
                        controller.addAccountSavings(initialDepositSpinner.getValue(),accHolder);
                    }
                    else {
                        controller.addAccountCurrent(initialDepositSpinner.getValue(),accHolder);
                    }
                    setAccountChoiceBox();
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
        removeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    controller.removeAccountByAccountHolder(accHolder,accountChoiceBox.getValue());
                    setAccountChoiceBox();
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

        addTransactionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage programStage = new Stage();
                Parent programRoot;

                Callback<Class<?>, Object> controllerFactory = type -> {
                    if (type == AddUserTransactionController.class) {
                        return new AddUserTransactionController(controller,accHolder,accountChoiceBox.getValue());
                    } else {
                        try {
                            return type.newInstance() ;
                        } catch (Exception exc) {
                            System.err.println("Could not load add user transaction controller "+type.getName());
                            throw new RuntimeException(exc);
                        }
                    }
                };
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add-user-transaction-view.fxml"));
                    fxmlLoader.setControllerFactory(controllerFactory);
                    fxmlLoader.setLocation(getClass().getResource("add-user-transaction-view.fxml"));
                    programRoot = fxmlLoader.load();
                    Scene programScene = new Scene(programRoot);
                    String css = this.getClass().getResource("start.css").toExternalForm();
                    programScene.getStylesheets().add(css);
                    programStage.setTitle("Transaction");
                    programStage.setScene(programScene);
                    programStage.show();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
    public void  setAccountChoiceBox(){
        accountChoiceBox.getItems().addAll(controller.getAccountsByUser(accHolder));
    }
    public void setTransactionsTableView(){
        transactionsItems.clear();
        recieverColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getReceiver().getAccNumber()));
        categoryColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCategory().getName()));
        valueColumn.setCellValueFactory(cellData->new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getValue())));
        dateColumn.setCellValueFactory(cellData->new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getDate().toString())));
        transactionsItems.addAll(controller.getTransactionsByAccount(accountChoiceBox.getValue()));
        transactionsTableView.setItems(transactionsItems);
    }
}

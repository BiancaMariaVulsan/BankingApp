package view;

import controller.Controller;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    private TextField initialDepositTextField;
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
        setAccountChoiceBox();

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    if (accountTypeChoiceBox.getValue().equals("Savings")){
                        controller.addAccountSavings(Double.parseDouble(initialDepositTextField.getText()),accHolder);
                    }
                    else {
                        controller.addAccountCurrent(Double.parseDouble(initialDepositTextField.getText()),accHolder);
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
        accountChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                System.out.println(observableValue.getValue());
                setTransactionsTableView(observableValue.getValue().intValue());
            }
        });
    }
    public void  setAccountChoiceBox(){
        accountChoiceBox.getItems().clear();
        System.out.println(controller.getAccountsByUser(accHolder).size());
        accountChoiceBox.getItems().addAll(controller.getAccountsByUser(accHolder));
    }
    public void setTransactionsTableView(int index){
        transactionsItems.clear();
        if (index < 0) {
            transactionsTableView.getItems().clear();
            return;
        }
        recieverColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getReceiver().getAccNumber()));
        categoryColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCategory().getName()));
        valueColumn.setCellValueFactory(cellData->new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getValue())));
        dateColumn.setCellValueFactory(cellData->new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getDate().toString())));
        transactionsItems.addAll(controller.getTransactionsByAccount(accountChoiceBox.getItems().get(index)));
        transactionsTableView.setItems(transactionsItems);
    }
}

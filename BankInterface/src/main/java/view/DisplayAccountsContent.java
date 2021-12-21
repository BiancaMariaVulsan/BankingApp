package view;

import controller.Controller;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import model.AccHolder;
import model.Current;
import model.Savings;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class DisplayAccountsContent implements Initializable {
    AccHolder accHolder;
    @FXML
    private TableView<Current> currentTableView;
    private ObservableList<Current> currentsItems = FXCollections.observableArrayList();
    @FXML
    private TableView<Savings> savingsTableView;
    private ObservableList<Savings> savingsItems = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Current,String> accNumberCurrentColumn;
    @FXML
    private TableColumn<Current,String> cardNumberCurrentColumn;
    @FXML
    private TableColumn<Current,String> balanceCurrentColumn;
    @FXML
    private TableColumn<Current,String> rateCurrentColumn;
    @FXML
    private TableColumn<Savings,String> accNumberSavingsColumn;
    @FXML
    private TableColumn<Savings,String > cardNumberSavingsColumn;
    @FXML
    private TableColumn<Savings,String> balanceSavingsColumn;

    @FXML
    private TableColumn<Savings,String> rateSavingsColumn;
    Controller controller;

    public DisplayAccountsContent(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCurrentTable();
        setSavingsTable();
    }
    public void setCurrentTable(){
        currentsItems.clear();
        accNumberCurrentColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAccNumber()));
        cardNumberCurrentColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getDebitCardNr())));
        currentsItems.addAll(controller.getCurrentsByUser(accHolder));
        currentTableView.setItems(currentsItems);
    }

    public  void setSavingsTable(){
        savingsItems.clear();
        accNumberSavingsColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAccNumber()));
        cardNumberSavingsColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getSafetyDepositBoxId())));
        savingsItems.addAll(controller.getSavingsByUser(accHolder));
        savingsTableView.setItems(savingsItems);
    }

}

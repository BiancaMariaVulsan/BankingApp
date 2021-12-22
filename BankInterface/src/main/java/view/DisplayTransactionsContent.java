package view;

import controller.Controller;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import model.AccHolder;
import model.Transaction;

import java.net.URL;
import java.util.ResourceBundle;

public class DisplayTransactionsContent implements Initializable {
    @FXML
    private TableView<Transaction> transactionsTableView;
    ObservableList<Transaction> transactionsItems = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Transaction,String > recieverColumn;
    @FXML
    private TableColumn<Transaction,String> categoryColumn;
    @FXML
    private TableColumn<Transaction,String> valueColumn;
    @FXML
    private TableColumn<Transaction,String> dateColumn;

    AccHolder accHolder;
    Controller controller;

    public DisplayTransactionsContent(AccHolder accHolder,Controller controller) {
        this.controller = controller;
        this.accHolder = accHolder;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        transactionsTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Transaction");
                alert.setContentText(transactionsTableView.getSelectionModel().getSelectedItem().getDescription());
                Button confirm = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                confirm.setDefaultButton(false);
                confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
                alert.showAndWait();
            }
        });
        setTransactionsTableView();
    }

    public void setTransactionsTableView(){
        transactionsItems.clear();
        recieverColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getReceiver().getAccNumber()));
        categoryColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCategory().getName()));
        valueColumn.setCellValueFactory(cellData->new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getValue())));
        dateColumn.setCellValueFactory(cellData->new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getDate().toString())));
        transactionsItems.addAll(controller.getAllTransactionsByUser(accHolder));
        transactionsTableView.setItems(transactionsItems);
    }
}

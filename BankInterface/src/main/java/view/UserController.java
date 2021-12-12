package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    @FXML
    private PieChart transactionsChart;
    @FXML
    private ImageView exitImage;
    @FXML
    private ImageView statisticsImage;
    @FXML
    private ImageView displayTransactionsImage;
    @FXML
    private ImageView makeTransactionImage;
    @FXML
    private ImageView displayAccountsImage;
    @FXML
    private ImageView addAccountImage;
    @FXML
    private Button addAccountButton;
    @FXML
    private Button displayAccountsButton;
    @FXML
    private Button makeTransactionButton;
    @FXML
    private Button displayTransactionsButton;
    @FXML
    private Button displayStatisticsButton;
    @FXML
    private Button exitButton;

    private ArrayList<Button> buttons;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayAccountsButton.setDisable(true);
        buttons = new ArrayList<>();
        buttons.add(addAccountButton);
        buttons.add(displayAccountsButton);
        buttons.add(makeTransactionButton);
        buttons.add(displayTransactionsButton);
        buttons.add(displayStatisticsButton);
        for (Button button : buttons)
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    button.setDisable(true);
                    for (Button button1: buttons)
                        if (button1 != button)
                            button1.setDisable(false);
                }
            });
        transactionsChart.setTitle("Transactions");
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Food", 13),
                        new PieChart.Data("Utilities", 25),
                        new PieChart.Data("Clothing", 10),
                        new PieChart.Data("Healthcare", 22),
                        new PieChart.Data("Other", 30));
        transactionsChart.setData(pieChartData);

    }
}

package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class StatisticsContent implements Initializable {
    @FXML
    private PieChart transactionsChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

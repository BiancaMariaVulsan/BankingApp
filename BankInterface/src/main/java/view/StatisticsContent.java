package view;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Pane;
import model.AccHolder;
import model.Category;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StatisticsContent implements Initializable {
    AccHolder accHolder;
    @FXML
    private PieChart transactionsChart;
    Controller controller;

    public StatisticsContent(AccHolder accHolder, Controller controller) {
        this.accHolder = accHolder;
        this.controller = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        transactionsChart.setTitle("Transactions");
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        ArrayList<Category> categories = controller.getAllCategoriesName();
        for (Category category : categories){
            PieChart.Data data = new PieChart.Data(category.getName(),controller.getNumberOfTransactionByCategory(accHolder,category));
            pieChartData.add(data);
        }

        transactionsChart.setData(pieChartData);
    }
}

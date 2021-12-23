package view;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.AccHolder;
import model.Account;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    AccHolder accHolder;
    Controller controller;
    @FXML
    private PieChart transactionsChart;
    @FXML
    private Pane contentPane;

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

    public UserController(AccHolder accHolder, Controller controller) {
        this.accHolder = accHolder;
        System.out.println(accHolder.getFirstName());
        this.controller = controller;


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayStatisticsButton.setDisable(true);
        buttons = new ArrayList<>();
        buttons.add(addAccountButton);
        buttons.add(displayAccountsButton);
        buttons.add(makeTransactionButton);
        buttons.add(displayTransactionsButton);
        buttons.add(displayStatisticsButton);

        addAccountButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Button button = addAccountButton;
                addAccountButton.setDisable(true);
                for (Button button1: buttons)
                    if (button1 != button)
                        button1.setDisable(false);

                Callback<Class<?>, Object> controllerFactory = type -> {
                    if (type == AddAccountContent.class) {
                        return new AddAccountContent(accHolder,controller);
                    } else {
                        try {
                            return type.newInstance() ;
                        } catch (Exception exc) {
                            System.err.println("Could not load content "+type.getName());
                            throw new RuntimeException(exc);
                        }
                    }
                };
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add-account-view.fxml"));
                fxmlLoader.setControllerFactory(controllerFactory);
                fxmlLoader.setLocation(getClass().getResource("add-account-view.fxml"));
                try {
                    Pane contentAddAcount = fxmlLoader.load();
                    contentPane.getChildren().clear();
                    contentPane.getChildren().add(contentAddAcount);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        displayStatisticsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Button button = displayStatisticsButton;
                displayStatisticsButton.setDisable(true);
                for (Button button1: buttons)
                    if (button1 != button)
                        button1.setDisable(false);

                Callback<Class<?>, Object> controllerFactory = type -> {
                    if (type == StatisticsContent.class) {
                        return new StatisticsContent(accHolder,controller);
                    } else {
                        try {
                            return type.newInstance() ;
                        } catch (Exception exc) {
                            System.err.println("Could not load content "+type.getName());
                            throw new RuntimeException(exc);
                        }
                    }
                };
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("statistics-view.fxml"));
                fxmlLoader.setControllerFactory(controllerFactory);
                fxmlLoader.setLocation(getClass().getResource("statistics-view.fxml"));
                try {
                    Pane contentAddAcount = fxmlLoader.load();
                    contentPane.getChildren().clear();
                    contentPane.getChildren().add(contentAddAcount);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        displayAccountsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Button button = displayAccountsButton;
                displayAccountsButton.setDisable(true);
                for (Button button1: buttons)
                    if (button1 != button)
                        button1.setDisable(false);

                Callback<Class<?>, Object> controllerFactory = type -> {
                    if (type == DisplayAccountsContent.class) {
                        return new DisplayAccountsContent(accHolder,controller);
                    } else {
                        try {
                            return type.newInstance() ;
                        } catch (Exception exc) {
                            System.err.println("Could not load content "+type.getName());
                            throw new RuntimeException(exc);
                        }
                    }
                };
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("display-accounts-view.fxml"));
                fxmlLoader.setControllerFactory(controllerFactory);
                fxmlLoader.setLocation(getClass().getResource("display-accounts-view.fxml"));
                try {
                    Pane contentAddAcount = fxmlLoader.load();
                    contentPane.getChildren().clear();
                    contentPane.getChildren().add(contentAddAcount);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        makeTransactionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Button button = makeTransactionButton;
                makeTransactionButton.setDisable(true);
                for (Button button1: buttons)
                    if (button1 != button)
                        button1.setDisable(false);

                Callback<Class<?>, Object> controllerFactory = type -> {
                    if (type == AddTransactionContent.class) {
                        return new AddTransactionContent(accHolder,controller);
                    } else {
                        try {
                            return type.newInstance() ;
                        } catch (Exception exc) {
                            System.err.println("Could not load content "+type.getName());
                            throw new RuntimeException(exc);
                        }
                    }
                };
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add-transaction-view.fxml"));
                fxmlLoader.setControllerFactory(controllerFactory);
                fxmlLoader.setLocation(getClass().getResource("add-transaction-view.fxml"));
                try {
                    Pane contentAddAcount = fxmlLoader.load();
                    contentPane.getChildren().clear();
                    contentPane.getChildren().add(contentAddAcount);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        displayTransactionsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Button button = displayTransactionsButton;
                displayTransactionsButton.setDisable(true);
                for (Button button1: buttons)
                    if (button1 != button)
                        button1.setDisable(false);

                Callback<Class<?>, Object> controllerFactory = type -> {
                    if (type == DisplayTransactionsContent.class) {
                        return new DisplayTransactionsContent(accHolder,controller);
                    } else {
                        try {
                            return type.newInstance() ;
                        } catch (Exception exc) {
                            System.err.println("Could not load content "+type.getName());
                            throw new RuntimeException(exc);
                        }
                    }
                };
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("display-transactions-view.fxml"));
                fxmlLoader.setControllerFactory(controllerFactory);
                fxmlLoader.setLocation(getClass().getResource("display-transactions-view.fxml"));
                try {
                    Pane contentAddAcount = fxmlLoader.load();
                    contentPane.getChildren().clear();
                    contentPane.getChildren().add(contentAddAcount);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        displayAccountsButton.fire();
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
                    Stage stage = (Stage) addAccountButton.getScene().getWindow();
                    stage.close();
                }
            }
        });

    }
}

package view;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.Console;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InspectUserController implements Initializable {
    Controller controller;
    @FXML
    private Button addTransactionButton;

    public InspectUserController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addTransactionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage programStage = new Stage();
                Parent programRoot;

                Callback<Class<?>, Object> controllerFactory = type -> {
                    if (type == AddUserTransactionController.class) {
                        return new AddUserTransactionController(controller);
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
}

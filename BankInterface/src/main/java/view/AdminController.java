package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    @FXML
    private Button exitButton;
    @FXML
    private Button addUserButton;
    @FXML
    private TableView usersTableView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
                    Stage stage = (Stage) addUserButton.getScene().getWindow();
                    stage.close();
                }
            }
        });
        addUserButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //todo add user to list of users
                populateTableUsers();
            }
        });
        usersTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage programStage = new Stage();
                Parent programRoot;

                Callback<Class<?>, Object> controllerFactory = type -> {
                    if (type == InspectUserController.class) {
                        return new InspectUserController();
                    } else {
                        try {
                            return type.newInstance() ;
                        } catch (Exception exc) {
                            System.err.println("Could not load inspect user controller "+type.getName());
                            throw new RuntimeException(exc);
                        }
                    }
                };
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("inspect-user-view.fxml"));
                    fxmlLoader.setControllerFactory(controllerFactory);
                    fxmlLoader.setLocation(getClass().getResource("inspect-user-view.fxml"));
                    programRoot = fxmlLoader.load();
                    Scene programScene = new Scene(programRoot);
                    String css = this.getClass().getResource("start.css").toExternalForm();
                    programScene.getStylesheets().add(css);
                    programStage.setTitle("User");
                    programStage.setScene(programScene);
                    programStage.show();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public void populateTableUsers(){
    }
}

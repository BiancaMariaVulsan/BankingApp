package view;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

public class StartController {
    boolean isAdmin =true;
    Controller controller;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button signInButton;
    @FXML
    private Button registerButton;


    public void setUp(){

    }

    @FXML
    public void initialize(){
        // get from text fields username and password
        // if root and admin password -> isAdmin - true
        // else : isAdmin->false + lookup if user exists
        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle (ActionEvent e) {
                Stage programStage = new Stage();
                Parent programRoot;

                Callback<Class<?>, Object> controllerFactory = type -> {
                    if (type == RegisterController.class) {
                        return new RegisterController(controller);
                    } else {
                        try {
                            return type.newInstance() ;
                        } catch (Exception exc) {
                            System.err.println("Could not load register controller "+type.getName());
                            throw new RuntimeException(exc);
                        }
                    }
                };
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("register-view.fxml"));
                    fxmlLoader.setControllerFactory(controllerFactory);
                    fxmlLoader.setLocation(getClass().getResource("register-view.fxml"));
                    programRoot = fxmlLoader.load();
                    Scene programScene = new Scene(programRoot);
                    String css = this.getClass().getResource("start.css").toExternalForm();
                    programScene.getStylesheets().add(css);
                    programStage.setTitle("Running Program");
                    programStage.setScene(programScene);
                    programStage.show();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (usernameTextField.getText().isEmpty() || passwordTextField.getText().isEmpty())
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Program finished");
                    alert.setContentText("You must complete both username and password fields !");
                    Button confirm = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                    confirm.setDefaultButton(false);
                    confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
                    alert.showAndWait();
                    return;

                }
                if (usernameTextField.getText().equals("root") || passwordTextField.getText().equals("admin"))
                    isAdmin = true;
                else {
                    if (controller.checkUserExists(usernameTextField.getText(),passwordTextField.getText())){
                        isAdmin = false;
                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("There is no user with this username and password !");
                        Button confirm = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                        confirm.setDefaultButton(false);
                        confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
                        alert.showAndWait();
                        return;
                    }
                }
                if (isAdmin){
                    Stage programStage = new Stage();
                    Parent programRoot;

                    Callback<Class<?>, Object> controllerFactory = type -> {
                        if (type ==AdminController.class) {
                            return new AdminController(controller);
                        } else {
                            try {
                                return type.newInstance() ;
                            } catch (Exception exc) {
                                System.err.println("Could not load admin controller "+type.getName());
                                throw new RuntimeException(exc);
                            }
                        }
                    };
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("admin-view.fxml"));
                        fxmlLoader.setControllerFactory(controllerFactory);
                        fxmlLoader.setLocation(getClass().getResource("admin-view.fxml"));
                        programRoot = fxmlLoader.load();
                        Scene programScene = new Scene(programRoot);
                        String css = this.getClass().getResource("start.css").toExternalForm();
                        programScene.getStylesheets().add(css);
                        programStage.setTitle("Admin");
                        programStage.setScene(programScene);
                        programStage.show();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                else {
                    Stage programStage = new Stage();
                    Parent programRoot;
                    // todo call getter account holder by username
                    Callback<Class<?>, Object> controllerFactory = type -> {
                        if (type == UserController.class) {
                            return new UserController(controller);
                        } else {
                            try {
                                return type.newInstance() ;
                            } catch (Exception exc) {
                                System.err.println("Could not load user controller "+type.getName());
                                throw new RuntimeException(exc);
                            }
                        }
                    };
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("user-view.fxml"));
                        fxmlLoader.setControllerFactory(controllerFactory);
                        fxmlLoader.setLocation(getClass().getResource("user-view.fxml"));
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
            }
        });
    }
}
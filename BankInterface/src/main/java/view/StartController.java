package view;

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
    boolean isAdmin =false;

    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button signInButton;
    @FXML
    private Button registerButton;

    @FXML
    public void initialize(){
        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle (ActionEvent e) {
                Stage programStage = new Stage();
                Parent programRoot;

                Callback<Class<?>, Object> controllerFactory = type -> {
                    if (type == RegisterController.class) {
                        return new RegisterController();
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
                // todo check if both fields are completed and if user exists
                if (isAdmin){
                    Stage programStage = new Stage();
                    Parent programRoot;

                    Callback<Class<?>, Object> controllerFactory = type -> {
                        if (type ==AdminController.class) {
                            return new AdminController();
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

                    Callback<Class<?>, Object> controllerFactory = type -> {
                        if (type == UserController.class) {
                            return new UserController();
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
package view;

import controller.Controller;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.AccHolder;
import model.Account;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    @FXML
    private Button removeUserButton;
    @FXML
    private TableColumn<AccHolder,String> usernameColumn;
    @FXML
    private TableColumn<AccHolder,String> firstNameColumn;
    @FXML
    private TableColumn<AccHolder,String> lastNameColumn;
    @FXML
    private TableColumn<AccHolder,String> CNPColumn;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField CNPTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button exitButton;
    @FXML
    private Button addUserButton;
    @FXML
    private TableView<AccHolder> usersTableView;
    private ObservableList<AccHolder> usersItems = FXCollections.observableArrayList();

    Controller controller;

    public AdminController(Controller controller) {
        this.controller = controller;
    }

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
                try {
                    controller.addAccountHolder(firstNameTextField.getText(),lastNameTextField.getText(),CNPTextField.getText(),usernameTextField.getText(),passwordTextField.getText());
                    populateTableUsers();
                }catch (RuntimeException exception){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(exception.getMessage());
                    Button confirm = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                    confirm.setDefaultButton(false);
                    confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
                    alert.showAndWait();
                }

            }
        });
        removeUserButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    AccHolder accHolder = usersTableView.getSelectionModel().getSelectedItem();
                    controller.removeAccountHolder(accHolder);
                    populateTableUsers();
                }catch (RuntimeException exception){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(exception.getMessage());
                    Button confirm = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                    confirm.setDefaultButton(false);
                    confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
                    alert.showAndWait();
                }
            }
        });
        usersTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage programStage = new Stage();
                Parent programRoot;
                AccHolder accHolder = controller.getAccHolderByUsername(usersTableView.getSelectionModel().getSelectedItem().getUserName());
                Callback<Class<?>, Object> controllerFactory = type -> {
                    if (type == InspectUserController.class) {
                        return new InspectUserController(accHolder,controller);
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
        usersItems.clear();
        firstNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getFirstName()));
        lastNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getLastName()));
        CNPColumn.setCellValueFactory(cellData->new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getCnp())));
        usernameColumn.setCellValueFactory(cellData->new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getUserName())));
        usersItems.addAll(controller.getAllAccountHolders());
        usersTableView.setItems(usersItems);
        for(AccHolder accHolder : controller.getAllAccountHolders()) {
            System.out.println(accHolder.getFirstName());
        }
    }
}

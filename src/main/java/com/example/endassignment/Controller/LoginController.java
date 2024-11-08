package com.example.endassignment.Controller;

import com.example.endassignment.Database;
import com.example.endassignment.Model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    //database object
    Database database = new Database();
    User user = null;

    @FXML
    private TextField textField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label lblWarning;

    //login button logic
    @FXML
    public void handleLoginAction() {
        String username = textField.getText();
        String password = passwordField.getText();

        for (User u : database.users) {
            if (u.getUserName().equals(username) && u.getPassword().equals(password)) {
                user = u;
                break;
            }
        }
        if (user != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/endassignment/main-window.fxml"));
                MainWindowController mainWindowController = new MainWindowController(user, database);
                fxmlLoader.setController(mainWindowController);
                Parent root = fxmlLoader.load();

                Stage stage = new Stage();
                stage.setTitle("Main Window");
                stage.setScene(new Scene(root));
                stage.show();

                Stage loginStage = (Stage) textField.getScene().getWindow();
                loginStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            lblWarning.setText("Invalid Username or Password");
        }
    }
}

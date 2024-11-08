package com.example.endassignment.Controller;

import com.example.endassignment.Database;
import com.example.endassignment.Model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainWindowController {

    private User user;
    private Database database;
    ;

    @FXML
    private VBox contentArea;
    @FXML
    private Button btnManageShowings;
    @FXML
    private Button btnSellTickets;
    @FXML
    private Button btnViewSalesHistory;
    @FXML
    private Label welcomeLabel;

    public MainWindowController(User user, Database database) {
        this.user = user;
        this.database = database;

    }

    public void initialize() {
        btnManageShowings.setOnAction(event -> {
            openManageShowingsForm();
        });
        btnSellTickets.setOnAction(event -> {
            openSellTicketsForm();
        });
        btnViewSalesHistory.setOnAction(event -> {
            openSaleHistoryForm();
        });
        displayWelcomeMessage();
    }

    private void displayWelcomeMessage() {
        if (user != null) {
            String name = user.getUserName();
            String role = user.getRole().toString();
            String currentDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date());
            welcomeLabel.setText("Welcome " + name + "\nYou are logged in as " + role +
                    "\nCurrent date and time is " + currentDateTime);
        }
    }

    public void openManageShowingsForm() {
        if (user != null && user.getRole() == User.Role.management) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/endassignment/manageShowings.fxml"));
                ManageShowingsController manageShowingsController = new ManageShowingsController(database, contentArea);
                loader.setController(manageShowingsController);

                Parent root = loader.load();

                contentArea.getChildren().clear();
                contentArea.getChildren().add(root);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (user != null && user.getRole() == User.Role.service) {
            showAlert("Access Denied", "You do not have permission to access this page.");
        }
    }

    public void openSellTicketsForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/endassignment/sellTicketsForm.fxml"));
            SellTicketsController sellTicketsController = new SellTicketsController(database, contentArea);
            loader.setController(sellTicketsController);

            Parent root = loader.load();

            contentArea.getChildren().clear();
            contentArea.getChildren().add(root);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void openSaleHistoryForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/endassignment/salesHistoryForm.fxml"));
            SaleHistoryController saleHistoryController = new SaleHistoryController(database);
            loader.setController(saleHistoryController);

            Parent root = loader.load();
            contentArea.getChildren().clear();
            contentArea.getChildren().add(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}




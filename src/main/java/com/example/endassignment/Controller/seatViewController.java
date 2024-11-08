package com.example.endassignment.Controller;

import com.example.endassignment.Database;
import com.example.endassignment.Model.Customer;
import com.example.endassignment.Model.Sale;
import com.example.endassignment.Model.Show;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;

public class seatViewController {

    private final int ROW = 6;
    private final int COL = 12;

    private Database database;
    private Show currentShow;


    @FXML
    private VBox contentArea;
    @FXML
    private Label selectedShowingName;
    @FXML
    private GridPane seatsGrid;
    @FXML
    TextField customerNameField;
    @FXML
    ListView<String> currentSelectedSeats;
    @FXML
    Button buttonSellTickets;
    @FXML
    Button buttonCancel;

    public seatViewController(Database database, Show selectedShow, VBox contentArea) {
        this.database = database;
        this.currentShow = selectedShow;
        this.contentArea = contentArea;
    }

    public void initialize() {
        createSeats();
        selectedShowingName.setText(currentShow.getStartTime() + currentShow.getTitle());
        customerNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateSellButtonState();
        });
        buttonSellTickets.setOnAction(event -> {
            sellSeats();
        });
        buttonSellTickets.setText("Sell tickets");
        buttonCancel.setOnAction(event -> {
            handleCancel();
        });
        updateSellButtonState();

    }

    public void createSeats() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                ToggleButton seatButton = new ToggleButton();
                seatButton.setPrefSize(360, 360);
                seatButton.setText(String.valueOf(j + 1));
                int seatIndex = i * COL + j;

                if (currentShow.isSeatSold(seatIndex)) {
                    seatButton.setStyle("-fx-background-color: red");
                    selectSeats(i, j, seatIndex, seatButton);
                }
                final int row = i;
                final int col = j;
                seatButton.setOnAction(event -> {
                    {
                        selectSeats(row, col, seatIndex, seatButton);
                        seatButton.setDisable(false);
                    }
                });
                seatsGrid.add(seatButton, j, i);
            }
        }
    }

    public void selectSeats(int row, int col, int index, ToggleButton seatButton) {
        if (!currentShow.isSeatSold(index)) {
            String seatNumber = "Row " + (row + 1) + " / Seat " + (col + 1);
            if (seatButton.getStyle().contains("green")) {
                seatButton.setStyle("");
                currentSelectedSeats.getItems().remove(seatNumber);
            } else {
                seatButton.setStyle("-fx-background-color: green");
                currentSelectedSeats.getItems().add(seatNumber);
            }
            buttonSellTickets.setText("Sell " + currentSelectedSeats.getItems().size() + " tickets");
        }
    }

    public void sellSeats() {
        if (currentShow.isAgeCheckRequired()) {
            showAgeCheckDialog();
        } else {
            completePurchase();
        }

    }

    private void showAgeCheckDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/endassignment/ageCheckForm.fxml"));
            Parent root = loader.load();

            AgeController ageCheckController = loader.getController();
            ageCheckController.initialize(currentShow, customerNameField.getText(), currentSelectedSeats.getItems().size(), this);

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Age Verification");
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void completePurchase() {
        String customerName = customerNameField.getText();
        if (customerName.isEmpty()) {
            showAlert("Please enter the customer name.");
            return;
        }

        for (javafx.scene.Node node : seatsGrid.getChildren()) {

            if (node instanceof ToggleButton) {
                ToggleButton seatButton = (ToggleButton) node;
                if (seatButton.getStyle().contains("green")) {
                    int row = GridPane.getRowIndex(seatButton);
                    int col = GridPane.getColumnIndex(seatButton);
                    int seatIndex = row * COL + col;
                    currentShow.sellSeat(seatIndex);
                    seatButton.setStyle("-fx-background-color: red");
                    seatButton.setDisable(true);
                }
            }
        }
        Customer customer = new Customer(customerName);
        Sale sale = new Sale(LocalDateTime.now(), customer, currentShow, currentSelectedSeats.getItems().size());
        database.addSale(sale);

        // Clear the selections after sale
        currentSelectedSeats.getItems().clear();
        customerNameField.clear();

        showAlert("Tickets sold successfully.");
    }

    public void handleCancel() {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/endassignment/sellTicketsForm.fxml"));
            SellTicketsController sellTicketsController = new SellTicketsController(database, contentArea);
            fxmlLoader.setController(sellTicketsController);
            Parent root = fxmlLoader.load();

            contentArea.getChildren().clear();
            contentArea.getChildren().add(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void updateSellButtonText() {
        int selectedSeatsCount = currentSelectedSeats.getItems().size();
        if (selectedSeatsCount > 0) {
            buttonSellTickets.setText("Sell " + selectedSeatsCount + " ticket(s)");
        } else {
            buttonSellTickets.setText("Sell");
        }
    }

    private void updateSellButtonState() {
        String customerName = customerNameField.getText();
        buttonSellTickets.setDisable(customerName == null || customerName.trim().isEmpty());
    }
}

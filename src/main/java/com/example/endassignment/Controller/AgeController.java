package com.example.endassignment.Controller;

import com.example.endassignment.Model.Show;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AgeController {

    @FXML
    Label showName;
    @FXML
    Label showDate;
    @FXML
    Label numberOfTickets;
    @FXML
    Label customerName;
    @FXML
    Button buttonConfirm;
    @FXML
    Button buttonCancel;
    @FXML
    CheckBox ageCheckBox;

    private seatViewController parentController;


    public void initialize(Show show, String customerName, int ticketCount, seatViewController parentController) {
        this.showName.setText(show.getTitle());
        this.showDate.setText(show.getStartTime().toString());
        this.numberOfTickets.setText(String.valueOf(ticketCount));
        this.customerName.setText(customerName);
        this.parentController = parentController;

        buttonConfirm.setDisable(true);


        ageCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            buttonConfirm.setDisable(!newValue);
        });


        buttonConfirm.setOnAction(event -> {
            handleConfirm();
        });


        buttonCancel.setOnAction(event -> {
            handleCancel();
        });
    }

    private void handleConfirm() {
        parentController.completePurchase();
        closeWindow();
    }

    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        stage.close();
    }
}

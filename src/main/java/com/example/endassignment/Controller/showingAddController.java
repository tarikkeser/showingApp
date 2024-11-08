package com.example.endassignment.Controller;

import com.example.endassignment.Database;
import com.example.endassignment.Model.Show;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;


import java.time.LocalDateTime;

public class showingAddController {

    @FXML
    TextField showingTitle;
    @FXML
    DatePicker dateStartDay;
    @FXML
    DatePicker dateEndDay;
    @FXML
    TextField dateStartHour;
    @FXML
    TextField dateEndHour;
    @FXML
    Label errorMessage;

    private Database database;
    @FXML
    private VBox contentArea;
    @FXML
    Button buttonAdd;
    @FXML
    Button buttonCancel;

    public showingAddController(Database database, VBox contentArea) {
        this.database = database;
        this.contentArea = contentArea;
    }

    public void initialize() {
        buttonAdd.setOnAction(event -> {
            handleAdd();
        });
        buttonCancel.setOnAction(event -> {
            handleCancel();
        });
    }

    public void handleAdd() {
        try {
            String title = showingTitle.getText();
            String startDay = dateStartDay.getValue().toString();
            String endDay = dateEndDay.getValue().toString();
            String startHour = dateStartHour.getText();
            String endHour = dateEndHour.getText();


            String startDateTimeStr = startDay + " " + startHour;
            String endDateTimeStr = endDay + " " + endHour;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime startTime = LocalDateTime.parse(startDateTimeStr, formatter);
            LocalDateTime endTime = LocalDateTime.parse(endDateTimeStr, formatter);

            Show show = new Show(title, startTime, endTime, true);
            database.shows.add(show);
            errorMessage.setText("Show added successfully!");


        } catch (DateTimeException e) {
            errorMessage.setText("Please enter the date and time in correct format!");
        }
    }

    public void handleCancel() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/endassignment/manageShowings.fxml"));
            ManageShowingsController manageShowingsController = new ManageShowingsController(database, contentArea);
            fxmlLoader.setController(manageShowingsController);
            Parent root = fxmlLoader.load();

            contentArea.getChildren().clear();
            contentArea.getChildren().add(root);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

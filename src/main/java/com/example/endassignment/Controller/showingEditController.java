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

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class showingEditController {

    private Database database;
    @FXML
    private VBox contentArea;

    @FXML
    TextField editShowingTitle;
    @FXML
    DatePicker editDateStartDay;
    @FXML
    DatePicker editDateEndDay;
    @FXML
    TextField editDateStartHour;
    @FXML
    TextField editDateEndHour;
    @FXML
    Label EditErrorMessage;
    @FXML
    Button editButton;
    @FXML
    Button editButtonCancel;

    // Constructor
    public showingEditController(Database database, VBox contentArea) {
        this.database = database;
        this.contentArea = contentArea;
    }

    public void initialize(Show show) {

        editShowingTitle.setText(show.getTitle());
        editDateStartDay.setValue(show.getStartTime().toLocalDate());
        editDateEndDay.setValue(show.getEndTime().toLocalDate());
        editDateStartHour.setText(show.getStartTime().toLocalTime().toString());
        editDateEndHour.setText(show.getEndTime().toLocalTime().toString());

        editButton.setOnAction(event -> handleEdit(show));
        editButtonCancel.setOnAction(event -> handleCancel());
    }


    public void handleEdit(Show show) {
        try {
            String title = editShowingTitle.getText();
            String startDay = editDateStartDay.getValue().toString();
            String endDay = editDateEndDay.getValue().toString();
            String startHour = editDateStartHour.getText();
            String endHour = editDateEndHour.getText();


            String startDateTimeStr = startDay + " " + startHour;
            String endDateTimeStr = endDay + " " + endHour;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime startTime = LocalDateTime.parse(startDateTimeStr, formatter);
            LocalDateTime endTime = LocalDateTime.parse(endDateTimeStr, formatter);


            show.setTitle(title);
            show.setStartTime(startTime);
            show.setEndTime(endTime);

            EditErrorMessage.setText("Show updated successfully!");

        } catch (DateTimeException e) {
            EditErrorMessage.setText("Please enter the date and time in correct format!");
        } catch (NumberFormatException e) {
            EditErrorMessage.setText("Please enter a valid number for total seats!");
        } catch (Exception e) {
            EditErrorMessage.setText("An error occurred while updating the show.");
            e.printStackTrace();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

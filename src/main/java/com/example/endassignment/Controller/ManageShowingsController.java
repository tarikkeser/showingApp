package com.example.endassignment.Controller;

import com.example.endassignment.Database;
import com.example.endassignment.Model.Show;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ManageShowingsController {

    private ObservableList<Show> showList;
    private Database database;

    @FXML
    private VBox contentArea;

    public ManageShowingsController(Database database, VBox contentArea) {
        this.database = database;
        this.contentArea = contentArea;
    }

    @FXML
    TableView<Show> tableView;
    @FXML
    TableColumn<Show, String> startColumn;
    @FXML
    TableColumn<Show, String> endColumn;
    @FXML
    TableColumn<Show, String> titleColumn;
    @FXML
    TableColumn<Show, String> seatsLeftColumn;
    @FXML
    Button openButton;
    @FXML
    Button editButton;
    @FXML
    Button deleteButton;
    @FXML
    Button exportShowingsButton;
    @FXML
    Label lblWarning;

    public void initialize() {

        showList = FXCollections.observableArrayList(database.shows);
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        seatsLeftColumn.setCellValueFactory(new PropertyValueFactory<>("leftSeatsTotalSeats"));
        tableView.setItems(showList);
        openButton.setOnAction(event -> {
            openAddForm();
        });
        editButton.setOnAction(event -> {
            Show selectedShow = tableView.getSelectionModel().getSelectedItem();
            if (selectedShow != null) {
                openEditShowForm(selectedShow);
            } else {
                lblWarning.setText("Please Select a Show to Edit");
            }
        });
        deleteButton.setOnAction(event -> {
            Show selectedShow = tableView.getSelectionModel().getSelectedItem();
            if (selectedShow != null) {
                deleteShow(selectedShow);
            } else {
                lblWarning.setText("Please Select a Show to Delete");
            }
        });
        exportShowingsButton.setOnAction(event -> exportShowings());
    }

    public void openAddForm() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/endassignment/showingAddForm.fxml"));
            showingAddController showingAddController = new showingAddController(database, contentArea);
            fxmlLoader.setController(showingAddController);

            Parent root = fxmlLoader.load();

            contentArea.getChildren().clear();
            contentArea.getChildren().add(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openEditShowForm(Show show) {
        if (show != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/endassignment/showingEditForm.fxml"));
                showingEditController showingEditController = new showingEditController(database, contentArea);
                fxmlLoader.setController(showingEditController);
                Parent root = fxmlLoader.load();

                showingEditController.initialize(show);

                contentArea.getChildren().clear();
                contentArea.getChildren().add(root);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void deleteShow(Show show) {
        database.shows.remove(show);
        showList.remove(show);
    }

    public void exportShowings() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Showings to CSV");
        fileChooser.setInitialFileName("showings.csv");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(contentArea.getScene().getWindow());

        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("start datetime,end datetime,movie title,seats left");
                writer.newLine();

                for (Show show : showList) {
                    writer.write(show.getStartTime() + "," + show.getEndTime() + "," + show.getTitle() + "," + show.getLeftSeatsTotalSeats());
                    writer.newLine();
                }

                lblWarning.setText("Showings exported successfully!");
            } catch (IOException e) {
                e.printStackTrace();
                lblWarning.setText("Error exporting showings.");
            }
        }

    }
}

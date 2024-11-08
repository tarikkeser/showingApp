package com.example.endassignment.Controller;

import com.example.endassignment.Database;
import com.example.endassignment.Model.Show;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class SellTicketsController {

    private ObservableList<Show> showList;
    private Database database;

    @FXML
    private VBox contentArea;

    @FXML
    private Button selectSeat;

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
    Label lblWarning;

    @FXML
    TextArea filterShow;

    public SellTicketsController(Database database, VBox contentArea) {
        this.database = database;
        this.contentArea = contentArea;
    }

    public void initialize() {
        showList = FXCollections.observableArrayList(database.shows);
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        seatsLeftColumn.setCellValueFactory(new PropertyValueFactory<>("leftSeatsTotalSeats"));
        tableView.setItems(showList);
        selectSeat.setOnAction(event -> {
            openSeatView();
        });
        filterShow.textProperty().addListener((observable, oldValue, newValue) -> {
            filterShowings(newValue);
        });
    }

    public void openSeatView() {
        Show show = tableView.getSelectionModel().getSelectedItem();

        if (show != null) {
            lblWarning.setText(show.getTitle());
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/endassignment/seatView.fxml"));
                seatViewController seatViewController = new seatViewController(database, show, contentArea);
                fxmlLoader.setController(seatViewController);
                Parent root = fxmlLoader.load();
                contentArea.getChildren().clear();
                contentArea.getChildren().add(root);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            lblWarning.setText("Select a show first!");
        }
    }

    private void filterShowings(String filterText) {
        if (filterText.length() < 3) {
            tableView.setItems(showList);
        } else {
            String lowerCaseFilter = filterText.toLowerCase();
            ObservableList<Show> filteredList = FXCollections.observableArrayList();

            for (Show show : showList) {
                if (show.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                    filteredList.add(show);
                }
            }
            tableView.setItems(filteredList);
        }
    }


}

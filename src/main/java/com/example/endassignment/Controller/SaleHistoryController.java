package com.example.endassignment.Controller;

import com.example.endassignment.Database;
import com.example.endassignment.Model.Sale;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class SaleHistoryController {

    @FXML
    private TableView<Sale> saleTableView;

    @FXML
    private TableColumn<Sale, String> date;

    @FXML
    private TableColumn<Sale, Integer> numberOfTickets;

    @FXML
    private TableColumn<Sale, String> customerName;

    @FXML
    private TableColumn<Sale, String> showingName;

    private Database database;

    public SaleHistoryController(Database database) {
        this.database = database;
    }

    public void initialize() {
        date.setCellValueFactory(new PropertyValueFactory<>("saleDateTime"));
        numberOfTickets.setCellValueFactory(new PropertyValueFactory<>("numberOfTickets"));
        customerName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getName()));
        showingName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getShow().getTitle()));

        ObservableList<Sale> salesData = FXCollections.observableArrayList(database.getSales());
        saleTableView.setItems(salesData);
    }
}


package com.example.endassignment.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

public class Show {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String title;
    private int totalSeats;
    private ObservableList<Boolean> seats;
    private ObservableList<Customer> customers;
    private boolean isAgeCheckRequired;


    public Show(String title, LocalDateTime startTime, LocalDateTime endTime, boolean isAgeCheckRequired) {
        this.title = title;
        this.endTime = endTime;
        this.startTime = startTime;
        this.isAgeCheckRequired = isAgeCheckRequired;
        totalSeats = 72;
        seats = FXCollections.observableArrayList();
        for (int i = 0; i < totalSeats; i++) {
            seats.add(false);
        }
        customers = FXCollections.observableArrayList();
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSeatSold(int seatIndex) {
        return seats.get(seatIndex);
    }

    public void sellSeat(int seatIndex) {
        if (!seats.get(seatIndex)) {
            seats.set(seatIndex, true);
        }
    }

    public int getSoldSeatsCount() {
        int count = 0;
        for (Boolean seat : seats) {
            if (seat) {
                count++;
            }
        }
        return count;
    }

    public String getLeftSeatsTotalSeats() {
        return getSoldSeatsCount() + "/" + totalSeats;
    }

    public boolean isAgeCheckRequired() {
        return isAgeCheckRequired;
    }

    public void setAgeCheckRequired(boolean ageCheckRequired) {
        this.isAgeCheckRequired = ageCheckRequired;
    }
}

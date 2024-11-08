package com.example.endassignment.Model;

import java.time.LocalDateTime;

public class Sale {
    private LocalDateTime saleDateTime;
    private Customer customer;
    private Show show;
    private int numberOfTickets;

    public Sale(LocalDateTime saleDateTime, Customer customer, Show show, int numberOfTickets) {
        this.saleDateTime = saleDateTime;
        this.customer = customer;
        this.show = show;
        this.numberOfTickets = numberOfTickets;
    }

    public LocalDateTime getSaleDateTime() {
        return saleDateTime;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Show getShow() {
        return show;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }
}


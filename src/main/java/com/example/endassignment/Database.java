package com.example.endassignment;

import com.example.endassignment.Model.Customer;
import com.example.endassignment.Model.Sale;
import com.example.endassignment.Model.Show;
import com.example.endassignment.Model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Database {

    public List<User> users = new ArrayList<>();
    public ArrayList<Show> shows = new ArrayList<>();
    public List<Customer> customers = new ArrayList<>();
    public List<Sale> sales = new ArrayList<>();




    public Database() {
        User Manager = new User("tarik", "12345", User.Role.management);
        User Service = new User("john", "67890", User.Role.service);
        users.add(Manager);
        users.add(Service);
        //----------------

        Show show = new Show("Rebel Moon-Part Two:The Scargiver", LocalDateTime.of(2024, 10, 4, 14, 00, 00), LocalDateTime.of(2024, 10, 4, 16, 30, 00),true);
        Show show1 = new Show("Scarface", LocalDateTime.of(2024, 10, 4, 14, 00, 00), LocalDateTime.of(2024, 10, 4, 16, 30, 00),true);
        show.sellSeat(1);
        show.sellSeat(2);
        show.sellSeat(5);
        show.sellSeat(48);
        show.sellSeat(32);
        show1.sellSeat(1);
        show1.sellSeat(2);
        show1.sellSeat(5);
        show1.sellSeat(48);
        show1.sellSeat(32);
        shows.add(show);
        shows.add(show1);
        //----------------
        Customer customer = new Customer("Tarik Keser");
        Customer customer1 = new Customer("Wim Wiltenburg");
        Customer customer2 = new Customer("Erwin de Vries");
        customers.add(customer);
        customers.add(customer1);
        customers.add(customer2);
        //-----------------
        Sale sale1 = new Sale(LocalDateTime.now(), customer, show, 5);
        sales.add(sale1);

        Sale sale2 = new Sale(LocalDateTime.now(), customer1, show1, 5);
        sales.add(sale2);

    }

    public List<Sale> getSales() {
        return sales;
    }

    public void addSale(Sale sale) {
        sales.add(sale);
    }
}

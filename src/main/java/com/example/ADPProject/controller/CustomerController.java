package com.example.ADPProject.controller;

import com.example.ADPProject.model.Customer;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private List<Customer> customerList = new ArrayList<>();

    public CustomerController() {
        customerList.add(new Customer(1L, "Steve", "steve@abc.com", "pass"));
        customerList.add(new Customer(2L, "Bob", "bob@abc.com", "pass"));
        customerList.add(new Customer(3L, "Cindy", "cindy@abc.com", "pass"));
    }

    @GetMapping
    public List<Customer> getAll() {
        return customerList; // Return the hard-coded list of customers
    }

    @GetMapping("/{customerId}")
    public Customer getCustomerById(@PathVariable("customerId") Long id) {
        return customerList.stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst()
                .orElse(null); // Return customer or null if not found
    }
}

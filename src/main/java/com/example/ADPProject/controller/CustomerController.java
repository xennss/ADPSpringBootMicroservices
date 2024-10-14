package com.example.ADPProject.controller;

import com.example.ADPProject.model.Customer;
import com.example.ADPProject.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    private List<Customer> customerList = new ArrayList<>();

    public CustomerController() {
    }

    @GetMapping
    public List<Customer> getAll() {
       return customerRepository.findAll(); // Fetch all customers from the database
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("customerId") Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // Return 404 if not found
    }

    @PostMapping
    public ResponseEntity<?> addCustomer(@RequestBody Customer newCustomer , UriComponentsBuilder uri) {
        if (newCustomer.getId() != null) {
            return ResponseEntity.badRequest().build(); // Reject if ID is provided
        }
        newCustomer = customerRepository.save(newCustomer); // Save new customer
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newCustomer.getId())
                .toUri();
        return ResponseEntity.created(location).build(); // Return 201 Created
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<?> updateCustomer(@PathVariable("customerId") Long customerId,
                                         @RequestBody Customer updatedCustomer) {
        // Check if the customer exists
        Optional<Customer> existingCustomerOpt = customerRepository.findById(customerId);
        if (existingCustomerOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 if customer not found
        }

        Customer existingCustomer = existingCustomerOpt.get();

        // Only update the fields that are not null
        if (updatedCustomer.getName() != null) {
            existingCustomer.setName(updatedCustomer.getName());
        }
        if (updatedCustomer.getEmail() != null) {
            existingCustomer.setEmail(updatedCustomer.getEmail());
        }
        
        // Save the updated customer
        customerRepository.save(existingCustomer);
        
        return ResponseEntity.ok(existingCustomer); // Return updated customer
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable("customerId") Long id) {
        if (!customerRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 if not found
        }
        customerRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // Return 204 No Content
    }
}

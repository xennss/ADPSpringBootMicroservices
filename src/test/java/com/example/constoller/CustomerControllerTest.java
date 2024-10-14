package com.example.ADPProject.controller;

import com.example.ADPProject.model.Customer;
import com.example.ADPProject.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCustomers() {
        Customer customer1 = new Customer(1L, "Steve", "steve@abc.com", "pass");
        Customer customer2 = new Customer(2L, "Bob", "bob@abc.com", "pass");
        List<Customer> customers = Arrays.asList(customer1, customer2);

        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> result = customerController.getAll();

        System.out.println("Result: " + result); // Debugging statement

        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(customer1, customer2);
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    public void testGetCustomerById() {
        Customer customer = new Customer(1L, "Steve", "steve@abc.com", "pass");
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Customer result = customerController.getCustomerById(1L);

        System.out.println("Customer: " + result); // Debugging statement

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Steve");
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetCustomerByIdNotFound() {
        when(customerRepository.findById(99L)).thenReturn(Optional.empty());

        Customer result = customerController.getCustomerById(99L);

        System.out.println("Result for not found: " + result); // Debugging statement

        assertThat(result).isNull();
        verify(customerRepository, times(1)).findById(99L);
    }
}

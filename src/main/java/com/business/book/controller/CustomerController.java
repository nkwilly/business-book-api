package com.business.book.controller;

import com.business.book.entity.Customer;
import com.business.book.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    
    private final CustomerService customerService;
    
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        if (customerService.existsByEmail(customer.getEmail())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        if (customerService.existsByUsername(customer.getUsername())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Customer savedCustomer = customerService.save(customer);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }
    
    @GetMapping
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.findAll();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'SUPERADMIN')")
    public ResponseEntity<Customer> getCustomerById(@PathVariable UUID id) {
        return customerService.findById(id)
                .map(customer -> new ResponseEntity<>(customer, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/by-email/{email}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<Customer> getCustomerByEmail(@PathVariable String email) {
        return customerService.findByEmail(email)
                .map(customer -> new ResponseEntity<>(customer, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/by-username/{username}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<Customer> getCustomerByUsername(@PathVariable String username) {
        return customerService.findByUsername(username)
                .map(customer -> new ResponseEntity<>(customer, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'SUPERADMIN')")
    public ResponseEntity<Customer> updateCustomer(@PathVariable UUID id, @RequestBody Customer customer) {
        return customerService.findById(id)
                .map(existingCustomer -> {
                    customer.setId(id);
                    Customer updatedCustomer = customerService.save(customer);
                    return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'SUPERADMIN')")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id) {
        return customerService.findById(id)
                .map(customer -> {
                    customerService.deleteById(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping("/{id}/create-enterprise")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Void> createEnterprise(@PathVariable UUID id) {
        return customerService.findById(id)
                .map(customer -> {
                    customerService.createEnterprise(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping("/{id}/delete-enterprise")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Void> deleteEnterprise(@PathVariable UUID id) {
        return customerService.findById(id)
                .map(customer -> {
                    customerService.deleteEnterprise(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
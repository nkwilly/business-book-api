package com.business.book.service.impl;

import com.business.book.entity.Customer;
import com.business.book.entity.Enterprise;
import com.business.book.repository.CustomerRepository;
import com.business.book.service.CommunicationWithOrganizationAPI;
import com.business.book.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final CommunicationWithOrganizationAPI organizationAPI;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, PasswordEncoder passwordEncoder,
            CommunicationWithOrganizationAPI organizationAPI) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.organizationAPI = organizationAPI;
    }

    @Override
    public Customer save(Customer customer) {
        if (customer.getId() == null) {
            customer.setId(UUID.randomUUID());
        }
        if (customer.getPassword() != null) {
            customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        }
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        return customerRepository.findById(id);
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public Optional<Customer> findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    @Override
    public void deleteById(UUID id) {
        customerRepository.deleteById(id);
    }

    @Override
    public void createEnterprise(UUID customerId) {

        Enterprise enterprise = new Enterprise();
        enterprise.setBusinessActorId(customerId);

        organizationAPI.createEnterprise(enterprise);
    }

    @Override
    public void deleteEnterprise(UUID customerId) {
        // Récupérer toutes les entreprises du client et les supprimer une à une
        List<Enterprise> enterprises = organizationAPI.getAllEnterprise();
        for (Enterprise enterprise : enterprises) {
            if (enterprise.getBusinessActorId().equals(customerId)) {
                organizationAPI.deleteEnterprise(enterprise);
            }
        }
    }

    public void deleteEnterprise(UUID customerId, UUID enterpriseId) {
        Enterprise enterprise = organizationAPI.getEnterpriseById(enterpriseId);
        if (enterprise != null && enterprise.getBusinessActorId().equals(customerId)) {
            organizationAPI.deleteEnterprise(enterprise);
        } else {
            throw new SecurityException("Le client n'est pas propriétaire de cette entreprise.");
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return customerRepository.existsByUsername(username);
    }
}
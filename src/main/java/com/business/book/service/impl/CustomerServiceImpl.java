package com.business.book.service.impl;

import com.business.book.entity.Customer;
import com.business.book.entity.Enterprise;
import com.business.book.entity.constants.Status;
import com.business.book.repository.CustomerRepository;
import com.business.book.repository.EnterpriseRepository;
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
    private final EnterpriseRepository enterpriseRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, PasswordEncoder passwordEncoder,
            EnterpriseRepository enterpriseRepository) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.enterpriseRepository = enterpriseRepository;
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
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (customerOpt.isPresent()) {
            Enterprise enterprise = new Enterprise();
            enterprise.setId(UUID.randomUUID());
            enterprise.setBusinessActorId(customerId);
            enterprise.setActive(true); // ou à ajuster
            enterprise.setRegistrationDate(new Date());
            enterprise.setStatus(Status.SOCIETE_ANONYME);

            enterpriseRepository.save(enterprise);
        }
    }

    @Override
    public void deleteEnterprise(UUID customerId) {
        List<Enterprise> enterprises = enterpriseRepository.findAllByBusinessActorId(customerId);
        for (Enterprise enterprise : enterprises) {
            enterpriseRepository.deleteById(enterprise.getId());
        }
    }

    public void deleteEnterprise(UUID customerId, UUID enterpriseId) {
        Optional<Enterprise> enterpriseOpt = enterpriseRepository.findById(enterpriseId);
        if (enterpriseOpt.isPresent()) {
            Enterprise enterprise = enterpriseOpt.get();
            if (enterprise.getBusinessActorId().equals(customerId)) {
                enterpriseRepository.deleteById(enterpriseId);
            } else {
                throw new SecurityException("Le client n'est pas propriétaire de cette entreprise.");
            }
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
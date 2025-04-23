package com.business.book.service.impl;

import com.business.book.entity.SuperAdmin;
import com.business.book.entity.Customer;
import com.business.book.entity.EnterpriseData;
import com.business.book.entity.Traffic;
import com.business.book.repository.CustomerRepository;
import com.business.book.repository.EnterpriseDataRepository;
import com.business.book.repository.SuperAdminRepository;
import com.business.book.repository.TrafficRepository;
import com.business.book.service.SuperAdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SuperAdminServiceImpl implements SuperAdminService {

    private final SuperAdminRepository superAdminRepository;
    private final EnterpriseDataRepository enterpriseDataRepository;
    private final CustomerRepository customerRepository;
    private final TrafficRepository trafficRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SuperAdminServiceImpl(SuperAdminRepository superAdminRepository,
            EnterpriseDataRepository enterpriseDataRepository,
            CustomerRepository customerRepository,
            TrafficRepository trafficRepository,
            PasswordEncoder passwordEncoder) {
        this.superAdminRepository = superAdminRepository;
        this.enterpriseDataRepository = enterpriseDataRepository;
        this.customerRepository = customerRepository;
        this.trafficRepository = trafficRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public SuperAdmin save(SuperAdmin superAdmin) {
        if (superAdmin.getId() == null) {
            superAdmin.setId(UUID.randomUUID());
        }
        if (superAdmin.getPassword() != null) {
            superAdmin.setPassword(passwordEncoder.encode(superAdmin.getPassword()));
        }
        return superAdminRepository.save(superAdmin);
    }

    @Override
    public List<SuperAdmin> findAll() {
        return superAdminRepository.findAll();
    }

    @Override
    public Optional<SuperAdmin> findById(UUID id) {
        return superAdminRepository.findById(id);
    }

    @Override
    public Optional<SuperAdmin> findByEmail(String email) {
        return superAdminRepository.findByEmail(email);
    }

    @Override
    public Optional<SuperAdmin> findByUsername(String username) {
        return superAdminRepository.findByUsername(username);
    }

    @Override
    public void deleteById(UUID id) {
        superAdminRepository.deleteById(id);
    }

    @Override
    public void hinderEnterprise(UUID superAdminId, String enterpriseId) {
        Optional<SuperAdmin> superAdminOpt = superAdminRepository.findById(superAdminId);
        if (superAdminOpt.isPresent()) {
            Optional<EnterpriseData> enterpriseOpt = enterpriseDataRepository
                    .findByEnterpriseId(UUID.fromString(enterpriseId));
            if (enterpriseOpt.isPresent()) {
                EnterpriseData enterprise = enterpriseOpt.get();
                enterprise.setHindered(true); 
                enterpriseDataRepository.save(enterprise);
            }
        }
    }

    @Override
    public void hinderCustomer(UUID superAdminId, String customerId) {
        Optional<SuperAdmin> superAdminOpt = superAdminRepository.findById(superAdminId);
        if (superAdminOpt.isPresent()) {
            Optional<Customer> customerOpt = customerRepository.findById(UUID.fromString(customerId));
            if (customerOpt.isPresent()) {
                Customer customer = customerOpt.get();
                customer.setHindered(true);
                customerRepository.save(customer);
            }
        }
    }

    @Override
    public List<Traffic> getAllTraffics(UUID superAdminId) {
        return trafficRepository.findAll();
    }
}

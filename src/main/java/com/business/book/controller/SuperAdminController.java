package com.business.book.controller;

import com.business.book.entity.SuperAdmin;
import com.business.book.service.SuperAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/super-admins")
@PreAuthorize("hasRole('SUPERADMIN')")
public class SuperAdminController {
    
    private final SuperAdminService superAdminService;
    
    @Autowired
    public SuperAdminController(SuperAdminService superAdminService) {
        this.superAdminService = superAdminService;
    }
    
    @PostMapping
    public ResponseEntity<SuperAdmin> createSuperAdmin(@RequestBody SuperAdmin superAdmin) {
        SuperAdmin savedSuperAdmin = superAdminService.save(superAdmin);
        return new ResponseEntity<>(savedSuperAdmin, HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<SuperAdmin>> getAllSuperAdmins() {
        List<SuperAdmin> superAdmins = superAdminService.findAll();
        return new ResponseEntity<>(superAdmins, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SuperAdmin> getSuperAdminById(@PathVariable UUID id) {
        return superAdminService.findById(id)
                .map(superAdmin -> new ResponseEntity<>(superAdmin, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/by-email/{email}")
    public ResponseEntity<SuperAdmin> getSuperAdminByEmail(@PathVariable String email) {
        return superAdminService.findByEmail(email)
                .map(superAdmin -> new ResponseEntity<>(superAdmin, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/by-username/{username}")
    public ResponseEntity<SuperAdmin> getSuperAdminByUsername(@PathVariable String username) {
        return superAdminService.findByUsername(username)
                .map(superAdmin -> new ResponseEntity<>(superAdmin, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<SuperAdmin> updateSuperAdmin(@PathVariable UUID id, @RequestBody SuperAdmin superAdmin) {
        return superAdminService.findById(id)
                .map(existingSuperAdmin -> {
                    superAdmin.setId(id);
                    SuperAdmin updatedSuperAdmin = superAdminService.save(superAdmin);
                    return new ResponseEntity<>(updatedSuperAdmin, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSuperAdmin(@PathVariable UUID id) {
        return superAdminService.findById(id)
                .map(superAdmin -> {
                    superAdminService.deleteById(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping("/{id}/hinder-enterprise/{enterpriseId}")
    public ResponseEntity<Void> hinderEnterprise(@PathVariable UUID id, @PathVariable String enterpriseId) {
        return superAdminService.findById(id)
                .map(superAdmin -> {
                    superAdminService.hinderEnterprise(id, enterpriseId);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping("/{id}/hinder-customer/{customerId}")
    public ResponseEntity<Void> hinderCustomer(@PathVariable UUID id, @PathVariable String customerId) {
        return superAdminService.findById(id)
                .map(superAdmin -> {
                    superAdminService.hinderCustomer(id, customerId);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping("/{id}/get-all-traffics")
    public ResponseEntity<Void> getAllTraffics(@PathVariable UUID id) {
        return superAdminService.findById(id)
                .map(superAdmin -> {
                    superAdminService.getAllTraffics(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
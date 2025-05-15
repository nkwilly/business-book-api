package com.business.book.controller;

import com.business.book.entity.Enterprise;
import com.business.book.service.CommunicationWithOrganizationAPI;
import com.business.book.service.payload.request.CreateEnterpriseRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/enterprises")
public class EnterpriseController {

    private final CommunicationWithOrganizationAPI communicationAPI;

    public EnterpriseController(CommunicationWithOrganizationAPI communicationAPI) {
        this.communicationAPI = communicationAPI;
    }


    @PostMapping
    public ResponseEntity<Enterprise> createEnterprise(@RequestBody @Valid CreateEnterpriseRequest enterprise) {
        Enterprise createdEnterprise = communicationAPI.createEnterprise(enterprise);
        return ResponseEntity.ok(createdEnterprise);
    }

    @PutMapping
    public ResponseEntity<Enterprise> updateEnterprise(@RequestBody Enterprise enterprise) {
        Enterprise updatedEnterprise = communicationAPI.updateEnterprise(enterprise);
        return ResponseEntity.ok(updatedEnterprise);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteEnterprise(@RequestBody Enterprise enterprise) {
        boolean isDeleted = communicationAPI.deleteEnterprise(enterprise);
        return ResponseEntity.ok(isDeleted);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Enterprise> getEnterpriseById(@PathVariable UUID id) {
        Enterprise enterprise = communicationAPI.getEnterpriseById(id);
        return ResponseEntity.ok(enterprise);
    }
    
    // Bonjour le monde Je suis Willy Watcho
}

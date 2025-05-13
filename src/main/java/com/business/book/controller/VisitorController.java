package com.business.book.controller;

import com.business.book.entity.Visitor;
import com.business.book.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/visitors")
public class VisitorController {
    
    private final VisitorService visitorService;
    
    @Autowired
    public VisitorController(VisitorService visitorService) {
        this.visitorService = visitorService;
    }
    
    @PostMapping
    public ResponseEntity<Visitor> createVisitor(@RequestBody Visitor visitor) {
        Visitor savedVisitor = visitorService.save(visitor);
        return new ResponseEntity<>(savedVisitor, HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<Visitor>> getAllVisitors() {
        List<Visitor> visitors = visitorService.findAll();
        return new ResponseEntity<>(visitors, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Visitor> getVisitorById(@PathVariable UUID id) {
        return visitorService.findById(id)
                .map(visitor -> new ResponseEntity<>(visitor, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping("/{id}/search")
    public ResponseEntity<Void> search(@PathVariable UUID id) {
        return visitorService.findById(id)
                .map(visitor -> {
                    visitorService.search(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping("/{id}/subscribe")
    public ResponseEntity<Void> subscribe(@PathVariable UUID id) {
        return visitorService.findById(id)
                .map(visitor -> {
                    visitorService.subscribe(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping("/{id}/contact-enterprise")
    public ResponseEntity<Void> contactEnterprise(@PathVariable UUID id, @RequestBody String contact) {
        return visitorService.findById(id)
                .map(visitor -> {
                    visitorService.contactEnterprise(id, contact);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVisitor(@PathVariable UUID id) {
        return visitorService.findById(id)
                .map(visitor -> {
                    visitorService.deleteById(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
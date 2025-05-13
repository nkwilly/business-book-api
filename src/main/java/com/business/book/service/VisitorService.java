package com.business.book.service;

import com.business.book.entity.Visitor;
import com.business.book.entity.constants.Action;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VisitorService {
    Visitor save(Visitor visitor);
    List<Visitor> findAll();
    Optional<Visitor> findById(UUID id);
    void deleteById(UUID id);
    void search(UUID visitorId);
    void subscribe(UUID visitorId);
    void contactEnterprise(UUID visitorId, String contact);
    void saveVisitorActivity(UUID visitorId, Action action, String details);
}
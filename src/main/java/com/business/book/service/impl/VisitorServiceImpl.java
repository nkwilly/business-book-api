package com.business.book.service.impl;

import com.business.book.entity.Visitor;
import com.business.book.entity.constants.Action;
import com.business.book.repository.VisitorActivityRepository;
import com.business.book.repository.VisitorRepository;
import com.business.book.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VisitorServiceImpl implements VisitorService {

    private final VisitorRepository visitorRepository;
    private final VisitorActivityRepository visitorActivityRepository;

    @Autowired
    public VisitorServiceImpl(VisitorRepository visitorRepository,
            VisitorActivityRepository visitorActivityRepository) {
        this.visitorRepository = visitorRepository;
        this.visitorActivityRepository = visitorActivityRepository;
    }

    @Override
    public Visitor save(Visitor visitor) {
        if (visitor.getId() == null) {
            visitor.setId(UUID.randomUUID());
        }
        return visitorRepository.save(visitor);
    }

    @Override
    public List<Visitor> findAll() {
        return visitorRepository.findAll();
    }

    @Override
    public Optional<Visitor> findById(UUID id) {
        return visitorRepository.findById(id);
    }

    @Override
    public void deleteById(UUID id) {
        visitorRepository.deleteById(id);
    }

    @Override
    public void search(UUID visitorId) {
        Optional<Visitor> visitorOpt = visitorRepository.findById(visitorId);
        if (visitorOpt.isPresent()) {
            Visitor visitor = visitorOpt.get();

            // A implementer
            visitorActivityRepository.saveVisitorActivity(visitor.getId(), Action.SEARCH, "");
        }
    }

    @Override
    public void saveVisitorActivity(UUID visitorId, Action action, String details) {
        visitorActivityRepository.saveVisitorActivity(visitorId, action, details);
    }

    @Override
    public void subscribe(UUID visitorId) {
        Optional<Visitor> visitorOpt = visitorRepository.findById(visitorId);
        if (visitorOpt.isPresent()) {
            Visitor visitor = visitorOpt.get();
            visitor.setSubscribed(true);
            visitorRepository.save(visitor);

            visitorActivityRepository.saveVisitorActivity(visitor.getId(), Action.SUBSCRIBE, "");
        }
    }

    @Override
    public void contactEnterprise(UUID visitorId, String contact) {
        Optional<Visitor> visitorOpt = visitorRepository.findById(visitorId);
        if (visitorOpt.isPresent()) {
            Visitor visitor = visitorOpt.get();
            System.out.println("Visitor " + visitor.getId() + " contacted enterprise: " + contact);

            visitorActivityRepository.saveVisitorActivity(visitor.getId(), Action.CONTACT_ENTERPRISE, contact);
        }
    }

}
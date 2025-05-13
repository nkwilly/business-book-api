package com.business.book.repository;

import com.business.book.entity.VisitorActivity;
import com.business.book.entity.constants.Action;
import com.business.book.service.impl.VisitorServiceImpl;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface VisitorActivityRepository extends CassandraRepository<VisitorActivity, UUID> {
    List<VisitorActivity> findByVisitorId(UUID visitorId);

    public default void saveVisitorActivity(UUID visitorId, Action action, String details) {
    
        VisitorActivity activity = new VisitorActivity();
        activity.setVisitorId(visitorId);
        activity.setActivityTime(LocalDateTime.now());
        activity.setAction(action);
        activity.setDetails(details);
    
        save(activity);
    }

    default void saveVisitorActivity(VisitorServiceImpl visitorServiceImpl, UUID visitorId, Action actionName) {
        visitorServiceImpl.saveVisitorActivity(visitorId, actionName, null);
    }
}

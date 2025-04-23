package com.business.book.service;

import com.business.book.entity.Traffic;

import java.util.Optional;
import java.util.UUID;

public interface TrafficService {

    Optional<Traffic> getTrafficByEnterpriseId(UUID enterpriseId);

    void incrementView(UUID enterpriseId);

    void incrementContactClicks(UUID enterpriseId);

    void incrementSubscriptions(UUID enterpriseId);
}

package com.business.book.service.impl;

import com.business.book.entity.Traffic;
import com.business.book.repository.TrafficRepository;
import com.business.book.service.TrafficService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TrafficServiceImpl implements TrafficService {

    private final TrafficRepository trafficRepository;

    @Autowired
    public TrafficServiceImpl(TrafficRepository trafficRepository) {
        this.trafficRepository = trafficRepository;
    }

    @Override
    public Optional<Traffic> getTrafficByEnterpriseId(UUID enterpriseId) {
        return trafficRepository.findByEnterpriseId(enterpriseId);
    }

    @Override
    public void incrementView(UUID enterpriseId) {
        Traffic traffic = trafficRepository.findByEnterpriseId(enterpriseId)
                .orElse(new Traffic());
        traffic.setEnterpriseId(enterpriseId);
        traffic.setViewsCount(traffic.getViewsCount() + 1);
        trafficRepository.save(traffic);
    }

    @Override
    public void incrementContactClicks(UUID enterpriseId) {
        Traffic traffic = trafficRepository.findByEnterpriseId(enterpriseId)
                .orElse(new Traffic());
        traffic.setEnterpriseId(enterpriseId);
        traffic.setContactClicks(traffic.getContactClicks() + 1);
        trafficRepository.save(traffic);
    }

    @Override
    public void incrementSubscriptions(UUID enterpriseId) {
        Traffic traffic = trafficRepository.findByEnterpriseId(enterpriseId)
                .orElse(new Traffic());
        traffic.setEnterpriseId(enterpriseId);
        traffic.setSubscriptions(traffic.getSubscriptions() + 1);
        trafficRepository.save(traffic);
    }
}

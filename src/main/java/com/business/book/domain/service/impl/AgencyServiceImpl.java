package com.business.book.domain.service.impl;

import com.business.book.domain.service.AgencyService;
import com.business.book.infrastructure.entity.Agency;
import com.business.book.infrastructure.repository.AgencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AgencyServiceImpl implements AgencyService {
    private final AgencyRepository agencyRepository;

    public Flux<Agency> findAll() {
        return agencyRepository.findAll();
    }

    public Mono<Agency> findById(UUID id) {
        return agencyRepository.findById(id);
    }

    public Mono<Agency> save(Agency agency) {
        return agencyRepository.save(agency);
    }

    public Mono<Void> deleteById(UUID id) {
        return agencyRepository.deleteById(id);
    }
}


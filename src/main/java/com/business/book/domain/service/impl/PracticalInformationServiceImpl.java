package com.business.book.domain.service.impl;

import com.business.book.domain.service.PracticalInformationService;
import com.business.book.infrastructure.entity.Agency;
import com.business.book.infrastructure.entity.PracticalInformation;
import com.business.book.infrastructure.repository.PracticalInformationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PracticalInformationServiceImpl implements PracticalInformationService {
    private final PracticalInformationRepository practicalInformationRepository;

    public Flux<PracticalInformation> findAll() {
        return practicalInformationRepository.findAll();
    }

    public Mono<PracticalInformation> findById(UUID id) {
        return practicalInformationRepository.findById(id);
    }

    public Mono<PracticalInformation> save(PracticalInformation info) {
        return practicalInformationRepository.save(info);
    }

    public Mono<Void> deleteById(UUID id) {
        return practicalInformationRepository.deleteById(id);
    }
}

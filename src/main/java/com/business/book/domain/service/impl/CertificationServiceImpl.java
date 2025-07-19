package com.business.book.domain.service.impl;

import com.business.book.domain.service.CertificationService;
import com.business.book.infrastructure.entity.Certification;
import com.business.book.infrastructure.repository.CertificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CertificationServiceImpl implements CertificationService {
    private final CertificationRepository certificationRepository;

    public Flux<Certification> findAll() {
        return certificationRepository.findAll();
    }

    public Mono<Certification> findById(UUID id) {
        return certificationRepository.findById(id);
    }

    public Mono<Certification> save(Certification certification) {
        return certificationRepository.save(certification);
    }

    public Mono<Void> deleteById(UUID id) {
        return certificationRepository.deleteById(id);
    }
}
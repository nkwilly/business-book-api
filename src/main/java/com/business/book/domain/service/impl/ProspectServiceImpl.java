package com.business.book.domain.service.impl;

import com.business.book.domain.service.ProspectService;
import com.business.book.infrastructure.entity.Prospect;
import com.business.book.infrastructure.repository.ProspectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProspectServiceImpl implements ProspectService {
    private final ProspectRepository prospectRepository;

    public Flux<Prospect> findAll() {
        return prospectRepository.findAll();
    }

    public Mono<Prospect> findById(UUID id) {
        return prospectRepository.findById(id);
    }

    public Mono<Prospect> save(Prospect prospect) {
        return prospectRepository.save(prospect);
    }

    public Mono<Void> deleteById(UUID id) {
        return prospectRepository.deleteById(id);
    }
}

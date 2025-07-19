package com.business.book.domain.service.impl;

import com.business.book.domain.service.ActivityService;
import com.business.book.infrastructure.entity.Activity;
import com.business.book.infrastructure.repository.ActivityRepository;
import com.business.book.presentation.dto.ActivityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepository activityRepository;

    private final WebClient webClient;

    @Value("${base.url}")
    private String baseUrl;

    public Flux<Activity> findAll() {
        return activityRepository.findAll();
    }

    public Mono<Activity> findById(UUID id) {
        return activityRepository.findById(id);
    }

    public Mono<Activity> save(Activity activity) {
        return activityRepository.save(activity);
    }

    public Mono<Void> deleteById(UUID id) {
        return activityRepository.deleteById(id);
    }
}

package com.business.book.presentation.controller;

import com.business.book.domain.service.ActivityService;
import com.business.book.infrastructure.entity.Activity;
import com.business.book.infrastructure.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {
    private final ActivityService activityService;
    private final ActivityRepository activityRepository;

    @GetMapping
    public Flux<Activity> getAll() {
        return activityService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Activity>> getById(@PathVariable UUID id) {
        return activityService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/organization/{organizationId}")
    public Flux<?> getByOrganizationId(@PathVariable UUID organizationId) {
        return activityRepository.findByOrganizationId(organizationId);
    }

    @PostMapping
    public Mono<Activity> create(@RequestBody Activity activity) {
        return activityService.save(activity);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable UUID id) {
        return activityService.deleteById(id)
                .thenReturn(ResponseEntity.noContent().build());
    }
}


package com.business.book.controller;

import com.business.book.entity.Traffic;
import com.business.book.service.TrafficService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/traffics")
public class TrafficController {

    private final TrafficService trafficService;

    @Autowired
    public TrafficController(TrafficService trafficService) {
        this.trafficService = trafficService;
    }

    // Accès : ADMIN, ENTREPRISE PROPRIÉTAIRE, CLIENT AYANT CRÉÉ L'ENTREPRISE
    @GetMapping("/{enterpriseId}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isEnterpriseOwner(#enterpriseId)")
    public ResponseEntity<Traffic> getTraffic(@PathVariable UUID enterpriseId) {
        Optional<Traffic> traffic = trafficService.getTrafficByEnterpriseId(enterpriseId);
        return traffic.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Exemple : endpoint pour incrémenter les vues si nécessaire
    @PostMapping("/{enterpriseId}/view")
    public ResponseEntity<Void> addView(@PathVariable UUID enterpriseId) {
        trafficService.incrementView(enterpriseId);
        return ResponseEntity.ok().build();
    }
}

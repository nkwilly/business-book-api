package ink.yowyob.business.book.presentation.controller;

import ink.yowyob.business.book.domain.mapper.EnterpriseMapper;
import ink.yowyob.business.book.infrastructure.entity.Enterprise;
import ink.yowyob.business.book.infrastructure.repository.EnterpriseDataRepository;
import ink.yowyob.business.book.domain.service.CommunicationWithOrganizationAPI;
import ink.yowyob.business.book.presentation.dto.enterprise.EnterpriseDto;
import ink.yowyob.business.book.presentation.dto.SuccessDto;
import ink.yowyob.business.book.presentation.dto.enterprise.UpdateEnterpriseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/enterprises")
public class EnterpriseController {

    private final CommunicationWithOrganizationAPI communicationAPI;
    private final EnterpriseDataRepository dataRepository;
    private final EnterpriseMapper enterpriseMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Enterprise> createEnterprise(@RequestBody @Valid Enterprise enterprise) {
        // Remplacer le retour de SuccessDto par un simple EnterpriseDto pour éviter les problèmes de cache
        return communicationAPI.createEnterprise(enterprise);
    }

    @PutMapping
    public Mono<SuccessDto<EnterpriseDto>> updateEnterprise(@RequestBody UpdateEnterpriseDto enterprise) {
        return communicationAPI.updateEnterprise(enterprise)
                .map(enterpriseMapper::toEnterpriseDto)
                .map(updated -> SuccessDto.of(HttpStatus.OK, "Enterprise updated successfully", "/api/enterprises", updated));
    }

    @DeleteMapping("/{enterpriseId}")
    public Mono<SuccessDto<Void>> deleteEnterprise(@PathVariable UUID enterpriseId) {
        return communicationAPI.deleteEnterprise(enterpriseId)
                .then(Mono.just(SuccessDto.of(HttpStatus.OK, "Enterprise deleted successfully", "/api/enterprises/" + enterpriseId)));
    }

    @GetMapping("/{id}")
    public Mono<EnterpriseDto> getEnterpriseById(@PathVariable UUID id) {
        return communicationAPI.getEnterpriseById(id)
                .map(enterpriseMapper::toEnterpriseDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<EnterpriseDto> getAllEnterprises() {
        return communicationAPI.getAllEnterprise()
                .map(enterpriseMapper::toEnterpriseDto);
    }
}
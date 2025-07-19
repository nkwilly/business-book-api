package com.business.book.domain.service.impl;

import com.business.book.domain.exception.EntityNotFoundException;
import com.business.book.domain.service.ImageNameService;
import com.business.book.infrastructure.entity.*;
import com.business.book.infrastructure.repository.*;
import com.business.book.domain.service.CommunicationWithOrganizationAPI;
import com.business.book.presentation.dto.CreateEnterpriseDto;
import com.business.book.presentation.dto.LoginDto;
import com.business.book.utils.GeneratorsUtils;
import com.business.book.utils.SecurityUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.datastax.dse.driver.internal.core.graph.SearchPredicate.token;

@Slf4j
@Service
@Primary
@Transactional
@RequiredArgsConstructor
public class CommunicationWithOrganizationAPIImpl implements CommunicationWithOrganizationAPI {

    private final WebClient webClient;

    private final SecurityUtils securityUtils;

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    private final EnterpriseDataRepository enterpriseDataRepository;
    private final EnterpriseRepository enterpriseRepository;
    private final ActivityRepository activityRepository;
    private final AgencyRepository agencyRepository;
    private final CertificationRepository certificationRepository;
    private final PracticalInformationRepository practicalInformationRepository;
    private final ProspectRepository prospectRepository;
    private final ImageNameRepository imageNameRepository;

    @Value("${base.url}")
    private String baseUrl;

    @Override
    public Mono<Token> login(LoginDto loginRequest) throws RuntimeException {
        String url = "https://gateway.yowyob.com/auth-service/" + "/api/login";

        Mono<String> token = tokenRepository.findAll()
                .flatMap(tk -> Mono.just(tk.getContent()))
                .next();

        return webClient.post()
                .uri(url)
                .bodyValue(loginRequest)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .flatMap(jsonNode -> {
                    if (jsonNode == null || jsonNode.get("access_token").get("token") == null) {
                        log.error("Failed to login, jsonNode received is {}", jsonNode);
                        return Mono.error(new RuntimeException("Failed to authenticate"));
                    }
                    return authentication(jsonNode);
                })
                .onErrorResume(e -> {
                    log.error("Error during login: {}", e.getMessage());
                    return Mono.error(new RuntimeException("Failed to authenticate"));
                });
    }

    @Override
    public Mono<Enterprise> createEnterprise(CreateEnterpriseDto enterprise) {
        String url = baseUrl + "/organizations";
        ////Token token = this.findCurrentToken();
        return tokenRepository.findAll()
                .flatMap(tk -> Mono.just(tk.getContent()))
                .next()
                .flatMap(token ->
                        webClient.post()
                                .uri(url)
                                .header("Authorization", "Bearer " + token)
                                .bodyValue(enterprise)
                                .retrieve()
                                .onStatus(
                                        status -> status.is4xxClientError() || status.is5xxServerError(),
                                        response -> response.bodyToMono(String.class)
                                                .flatMap(errorBody -> Mono.error(new RuntimeException(
                                                        "HTTP Error: " + response.statusCode() + " - " + errorBody)))
                                )
                                .bodyToMono(Enterprise.class)
                                .flatMap(enterpriseRepository::save)
                )
                .onErrorResume(e -> {
                    log.error("Error during createEnterprise: {}", e.getMessage());
                    return Mono.error(new RuntimeException("Failed to create enterprise"));
                });
    }

    @Override
    public Mono<Enterprise> updateEnterprise(Enterprise enterprise) {
        String url = baseUrl + "/organizations/" + enterprise.getOrganizationId();
        //Token token = this.findCurrentToken();
        return tokenRepository.findAll()
                .flatMap(tk -> Mono.just(tk.getContent()))
                .next()
                .flatMap(token ->
                        webClient.put()
                                .uri(url)
                                .header("Authorization", "Bearer " + token)
                                .bodyValue(enterprise)
                                .retrieve()
                                .bodyToMono(Enterprise.class))
                .onErrorResume(e -> {
                    log.error("Error during updateEnterprise: {}", e.getMessage());
                    return Mono.error(new RuntimeException("Failed to update enterprise"));
                });
    }

    @Override
    public Mono<Void> deleteEnterprise(UUID enterpriseId) {
        String url = baseUrl + "/organizations/" + enterpriseId;
        //Token token = this.findCurrentToken();
        return tokenRepository.findAll()
                .flatMap(tk -> Mono.just(tk.getContent()))
                .next()
                .flatMap(token ->
                        webClient.delete()
                                .uri(url)
                                .header("Authorization", "Bearer " + token)
                                .retrieve()
                                .toBodilessEntity()
                                .flatMap(response -> {
                                    if (response.getStatusCode().is2xxSuccessful()) {
                                        return enterpriseRepository.deleteById(enterpriseId);
                                    } else {
                                        return Mono.error(new RuntimeException("Failed to delete enterprise: " + response.getStatusCode()));
                                    }
                                }))
                .onErrorResume(e -> {
                    log.error("Error during deleteEnterprise: {}", e.getMessage());
                    return Mono.error(new RuntimeException("Failed to delete enterprise", e));
                }).then();
    }

    @Override
    public Mono<Enterprise> getEnterpriseById(UUID organizationId) {
        return enterpriseRepository.findById(organizationId)
                .switchIfEmpty(
                        fetchEnterpriseById(organizationId)
                                .switchIfEmpty(Mono.error(new EntityNotFoundException("Organization not found with ID: " + organizationId)))
                );
    }

    @Override
    public Flux<Enterprise> getAllEnterprise() {
        return enterpriseRepository.findAll();
    }

    @Override
    @Scheduled(fixedDelayString = "3600000")
    public void updateAllEnterprisesInDatabase() {
        String url = baseUrl + "/organizations";
        tokenRepository.findAll()
                .flatMap(tk -> Mono.just(tk.getContent()))
                .next()
                .flatMapMany(tk ->
                                webClient.get()
                                        .uri(url)
                                        .header("Authorization", "Bearer " + tk)
                                        .retrieve()
                                        .bodyToFlux(Enterprise.class)
                                        .flatMap(enterpriseRepository::save)
                                /*
                                .flatMap(enterprise -> {
                                    return Flux.merge(
                                            fetchActivitiesByOrganization(enterprise.getOrganizationId()),
                                            fetchAgenciesByOrganization(enterprise.getOrganizationId()),
                                            fetchCertificationByOrganization(enterprise.getOrganizationId()),
                                            fetchImageNameByOrganization(enterprise.getOrganizationId()),
                                            fetchPracticalInformationByOrganization(enterprise.getOrganizationId()),
                                            fetchProspectByOrganization(enterprise.getOrganizationId())
                                    ).then(Mono.just(enterprise));
                                })*/
                )
                .onErrorResume(e -> {
                    log.error("Error during getAllEnterprise: {}", e.getMessage());
                    return Flux.empty();
                })
                .subscribe();
    }


    private Mono<Enterprise> fetchEnterpriseById(UUID organizationId) {
        String url = baseUrl + "/organizations";
        ////Token token = this.findCurrentToken();
        return tokenRepository.findAll()
                .flatMap(tk -> Mono.just(tk.getContent()))
                .next()
                .flatMapMany(token ->
                        webClient.get()
                                .uri(url)
                                .header("Authorization", "Bearer " + token)
                                .retrieve()
                                .bodyToMono(Enterprise.class))
                .next()
                .onErrorResume(e -> {
                    log.error("Error during getEnterpriseById: {}", e.getMessage());
                    return Mono.error(new RuntimeException("Failed to retrieve enterprise by ID"));
                });
    }

    private Flux<Activity> fetchActivitiesByOrganization(UUID organizationId) {
        String url = baseUrl + "/organizations/" + organizationId + "/proposed-activities";
        ////Token token = this.findCurrentToken();
        return tokenRepository.findAll()
                .flatMap(tk -> Mono.just(tk.getContent()))
                .next()
                .flatMapMany(token ->
                        webClient.get()
                                .uri(url)
                                .header("Authorization", "Bearer " + token)
                                .retrieve()
                                .bodyToFlux(Activity.class)
                                .flatMap(activity -> {
                                    activity.setOrganizationId(organizationId);
                                    return activityRepository.save(activity);
                                }))
                .onErrorResume(e -> {
                    log.error("Error during fetchActivitiesByOrganization: {}", e.getMessage());
                    return Flux.error(new RuntimeException("Failed to retrieve activities for organization"));
                });
    }

    private Flux<Certification> fetchCertificationByOrganization(UUID organizationId) {
        String url = baseUrl + "/organizations/" + organizationId + "/certifications";
        ////Token token = this.findCurrentToken();
        return tokenRepository.findAll()
                .flatMap(tk -> Mono.just(tk.getContent()))
                .next()
                .flatMapMany(token ->
                        webClient.get()
                                .uri(url)
                                .header("Authorization", "Bearer " + token)
                                .retrieve()
                                .bodyToFlux(Certification.class)
                                .flatMap(certification -> {
                                    certification.setOrganizationId(organizationId);
                                    return certificationRepository.save(certification);
                                }))
                .onErrorResume(e -> {
                    log.error("Error during fetchCertificationByOrganization: {}", e.getMessage());
                    return Flux.error(new RuntimeException("Failed to retrieve certifications for organization"));
                });
    }

    private Flux<Agency> fetchAgenciesByOrganization(UUID organizationId) {
        String url = baseUrl + "/organizations/" + organizationId + "/agencies";
        ////Token token = this.findCurrentToken();
        return tokenRepository.findAll()
                .flatMap(tk -> Mono.just(tk.getContent()))
                .next()
                .flatMapMany(token ->
                        webClient.get()
                                .uri(url)
                                .header("Authorization", "Bearer " + token)
                                .retrieve()
                                .bodyToFlux(Agency.class)
                                .flatMap(agency -> {
                                    agency.setOrganizationId(organizationId);
                                    return agencyRepository.save(agency);
                                }))
                .onErrorResume(e -> {
                    log.error("Error during fetchAgenciesByOrganization: {}", e.getMessage());
                    return Flux.error(new RuntimeException("Failed to retrieve agencies for organization"));
                });
    }

    private Flux<PracticalInformation> fetchPracticalInformationByOrganization(UUID organizationId) {
        String url = baseUrl + "/organizations/" + organizationId + "/practical-information";
        ////Token token = this.findCurrentToken();
        return tokenRepository.findAll()
                .flatMap(tk -> Mono.just(tk.getContent()))
                .next()
                .flatMapMany(token ->
                        webClient.get()
                                .uri(url)
                                .header("Authorization", "Bearer " + token)
                                .retrieve()
                                .bodyToFlux(JsonNode.class)
                                .flatMap(json -> {
                                    try {
                                        JsonNode dataNode = json.get("data");
                                        if (dataNode != null) {
                                            ObjectMapper objectMapper = new ObjectMapper();
                                            PracticalInformation info = objectMapper.treeToValue(dataNode, PracticalInformation.class);
                                            info.setOrganizationId(organizationId);
                                            return Mono.just(info);
                                        }
                                        return Mono.empty();
                                    } catch (Exception e) {
                                        //log.error("Error parsing practical information: {}", e.getMessage());
                                        return Mono.empty();
                                    }
                                })
                                .flatMap(practicalInformationRepository::save)
                )
                .onErrorResume(e -> {
                    //log.error("Error during fetchPracticalInformationByOrganization: {}", e.getMessage());
                    return Flux.error(new RuntimeException("Failed to retrieve practical information for organization " + e.getMessage()));
                });
    }

    private Flux<Prospect> fetchProspectByOrganization(UUID organizationId) {
        String url = baseUrl + "/organizations/" + organizationId + "/prospects";
        ////Token token = this.findCurrentToken();
        return tokenRepository.findAll()
                .flatMap(tk -> Mono.just(tk.getContent()))
                .next()
                .flatMapMany(token ->
                        webClient.get()
                                .uri(url)
                                .header("Authorization", "Bearer " + token)
                                .retrieve()
                                .bodyToFlux(Prospect.class)
                                .flatMap(prospect -> {
                                    prospect.setOrganizationId(organizationId);
                                    return prospectRepository.save(prospect);
                                }))
                .onErrorResume(e -> {
                    log.error("Error during fetchProspectByOrganization: {}", e.getMessage());
                    return Flux.error(new RuntimeException("Failed to retrieve prospects for organization"));
                });
    }

    private Mono<Token> findCurrentToken() {
        return tokenRepository.findTopByOrderBySaveAtDesc()
                .flatMap(token -> {
                    if (this.calculateSecondsSinceCreation(token.getSaveAt()) < 3600) {
                        return Mono.just(token);
                    } else {
                        return createNewToken();
                    }
                })
                .switchIfEmpty(createNewToken());
    }

    private Flux<ImageName> fetchImageNameByOrganization(UUID organizationId) {
        return imageNameRepository.findByOrganizationId(organizationId);
    }

    private Mono<Token> createNewToken() {
        LoginDto loginRequest = new LoginDto();
        loginRequest.setUsername("businessBook");
        loginRequest.setPassword("password");

        return this.login(loginRequest)
                .switchIfEmpty(Mono.error(new RuntimeException("Failed to authenticate")))
                .flatMap(tokenRepository::save);
    }

    private Mono<Token> authentication(JsonNode jsonNode) {
        JsonNode rolesNode = jsonNode.get("roles");
        List<String> roles = new ArrayList<>();
        if (rolesNode.isArray()) {
            for (JsonNode roleNode : rolesNode) {
                roles.add(roleNode.asText());
            }
        } else {
            roles.add(rolesNode.asText());
        }
        Token token = Token.builder()
                .userId(GeneratorsUtils.GenerateUUID())
                .content(jsonNode.get("access_token").get("token").asText())
                .roles(String.join(" ", roles))
                .saveAt(Instant.now())
                .expireAt(Instant.now().plusSeconds(jsonNode.get("access_token").get("expires_in").asLong()))
                .build();
        return tokenRepository.customSave(token);
    }

    private long calculateSecondsSinceCreation(Instant saveAt) {
        Instant now = Instant.now();
        Duration duration = Duration.between(saveAt, now);
        return duration.getSeconds();
    }
}

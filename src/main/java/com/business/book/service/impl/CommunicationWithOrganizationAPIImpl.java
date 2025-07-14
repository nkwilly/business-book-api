package com.business.book.service.impl;

import com.business.book.entity.Enterprise;
import com.business.book.entity.User;
import com.business.book.repository.EnterpriseDataRepository;
import com.business.book.repository.TokenRepository;
import com.business.book.repository.UserRepository;
import com.business.book.service.CommunicationWithOrganizationAPI;
import com.business.book.entity.Token;
import com.business.book.service.payload.request.CreateEnterpriseRequest;
import com.business.book.service.payload.request.LoginRequest;
import com.business.book.service.payload.request.RegisterRequest;
import com.business.book.service.utils.SecurityUtils;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
//@Primary
@Transactional
@RequiredArgsConstructor
public class CommunicationWithOrganizationAPIImpl implements CommunicationWithOrganizationAPI {

    private final WebClient webClient;

    private final SecurityUtils securityUtils;

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    private final EnterpriseDataRepository enterpriseDataRepository;

    @Value("${base.url}")
    private String baseUrl;

    @Value("${api.token}")
    private String token;

    @Override
    public Mono<Token> login(LoginRequest loginRequest) throws RuntimeException {
        String url = "https://gateway.yowyob.com/auth-service/" + "/api/login";

        return webClient.post()
                .uri(url)
                .bodyValue(loginRequest)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .flatMap(jsonNode -> {
                    if (jsonNode == null || jsonNode.get("access_token").get("token") == null) {
                        log.error("Failed to login, jsonNode received is {}" , jsonNode);
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
    public Mono<Enterprise> createEnterprise(CreateEnterpriseRequest enterprise) {
        String url = baseUrl + "/organizations";
        ////Token token = this.findCurrentToken();
        return webClient.post()
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
                .onErrorResume(e -> {
                    log.error("Error during createEnterprise: {}", e.getMessage());
                    return Mono.error(new RuntimeException("Failed to create enterprise"));
                });
    }

    @Override
    public Mono<Enterprise> updateEnterprise(Enterprise enterprise) {
        String url = baseUrl + "/organizations/" + enterprise.getOrganizationId();
        //Token token = this.findCurrentToken();
        return webClient.put()
                .uri(url)
                .header("Authorization", "Bearer " + token)
                .bodyValue(enterprise)
                .retrieve()
                .bodyToMono(Enterprise.class)
                .onErrorResume(e -> {
                    log.error("Error during updateEnterprise: {}", e.getMessage());
                    return Mono.error(new RuntimeException("Failed to update enterprise"));
                });
    }

    @Override
    public Mono<Void> deleteEnterprise(Enterprise enterprise) {
        String url = baseUrl + "/organizations/" + enterprise.getOrganizationId();
        //Token token = this.findCurrentToken();
        return webClient.delete()
                .uri(url)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toBodilessEntity()
                .flatMap(response -> {
                    if (response.getStatusCode().is2xxSuccessful()) {
                        return Mono.empty();
                    } else {
                        return Mono.error(new RuntimeException("Failed to delete enterprise: " + response.getStatusCode()));
                    }
                })
                .onErrorResume(e -> {
                    log.error("Error during deleteEnterprise: {}", e.getMessage());
                    return Mono.error(new RuntimeException("Failed to delete enterprise", e));
                }).then();
    }

    @Override
    public Mono<Enterprise> getEnterpriseById(UUID id) {
        String url = baseUrl + "/organizations/" + id.toString();
        //Token token = this.findCurrentToken();
        return webClient.get()
                .uri(url)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(Enterprise.class)
                .onErrorResume(e -> {
                    log.error("Error during getEnterpriseById: {}", e.getMessage());
                    return Mono.error(new RuntimeException("Failed to retrieve enterprise by ID"));
                });
    }

    @Override
    public Flux<Enterprise> getAllEnterprise() {
        String url = baseUrl + "/organizations";
        ////Token token = this.findCurrentToken();
        return webClient.get()
                .uri(url)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToFlux(Enterprise.class)
                .onErrorResume(e -> {
                    log.error("Error during getAllEnterprise: {}", e.getMessage());
                    return Flux.error(new RuntimeException("Failed to retrieve enterprises"));
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

    private Mono<Token> createNewToken() {
        LoginRequest loginRequest = new LoginRequest();
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
        Token token = new Token();
        token.setContent(jsonNode.get("access_token").get("token").asText());
        token.setRoles(String.join(" ", roles));
        token.setSaveAt(Instant.now());
        token.setExpireAt(Instant.now().plusSeconds(jsonNode.get("access_token").get("expires_in").asLong()));
        token.setId(UUID.randomUUID());
        Mono<Token> savedToken = tokenRepository.customSave(token);
        return savedToken;
    }

    private long calculateSecondsSinceCreation(Instant saveAt) {
        Instant now = Instant.now();
        Duration duration = Duration.between(saveAt, now);
        return duration.getSeconds();
    }
}

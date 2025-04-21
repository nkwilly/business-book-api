package com.business.book.service.impl;

import com.business.book.entity.Enterprise;
import com.business.book.entity.User;
import com.business.book.repository.EnterpriseDataRepository;
import com.business.book.repository.TokenRepository;
import com.business.book.repository.UserRepository;
import com.business.book.service.CommunicationWithOrganizationAPI;
import com.business.book.entity.Token;
import com.business.book.service.payload.dto.TokenDTO;
import com.business.book.service.payload.request.LoginRequest;
import com.business.book.service.payload.request.RegisterRequest;
import com.business.book.service.utils.SecurityUtils;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.awt.print.Pageable;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
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

    @Override
    public Token register(RegisterRequest registerRequest) throws RuntimeException {
        String url = baseUrl + "/auth/register";
        JsonNode jsonNode = webClient.post()
                .uri(url)
                .bodyValue(registerRequest)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        if (jsonNode == null || jsonNode.get("token") == null) {
            log.error("Failed to register, jsonNode received is {}", jsonNode);
            throw new RuntimeException("Failed to register");
        }
        return authentication(jsonNode);
    }

    @Override
    public Token login(LoginRequest loginRequest) throws RuntimeException {
        String url = baseUrl + "/auth/login";
        JsonNode jsonNode = webClient.post()
                .uri(url)
                .bodyValue(loginRequest)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
        if (jsonNode == null || jsonNode.get("token") == null) {
            log.error("Failed to login, jsonNode received is {}" , jsonNode);
            throw new RuntimeException("Failed to authenticate");
        }

        return authentication(jsonNode);
    }

    @Override
    public Enterprise createEnterprise(Enterprise enterprise) {
        String url = baseUrl + "/organizations";
        Token token = this.findCurrentToken();
        return webClient.post()
                .uri(url)
                .header("Authorization", "Bearer " + token.getToken())
                .bodyValue(enterprise)
                .retrieve()
                .bodyToMono(Enterprise.class)
                .block();
    }

    @Override
    public Enterprise updateEnterprise(Enterprise enterprise) {
        String url = baseUrl + "/organizations/" + enterprise.getId();
        Token token = this.findCurrentToken();
        return webClient.put()
                .uri(url)
                .header("Authorization", "Bearer " + token.getToken())
                .bodyValue(enterprise)
                .retrieve()
                .bodyToMono(Enterprise.class)
                .block();
    }

    @Override
    public boolean deleteEnterprise(Enterprise enterprise) {
        String url = baseUrl + "/organizations/" + enterprise.getId();
        Token token = this.findCurrentToken();
        return Boolean.TRUE.equals(webClient.delete()
                .uri(url)
                .header("Authorization", "Bearer " + token.getToken())
                .retrieve()
                .toBodilessEntity()
                .map(res -> res.getStatusCode().is2xxSuccessful())
                .onErrorReturn(false)
                .block());
    }

    @Override
    public Enterprise getEnterpriseById(UUID id) {
        String url = baseUrl + "/organizations/" + id;
        Token token = this.findCurrentToken();
        return webClient.get()
                .uri(url)
                .header("Authorization", "Bearer " + token.getToken())
                .retrieve()
                .bodyToMono(Enterprise.class)
                .block();
    }

    @Override
    public List<Enterprise> getAllEnterprise() {
        String url = baseUrl + "/organizations";
        Token token = this.findCurrentToken();
        return webClient.get()
                .uri(url)
                .header("Authorization", "Bearer " + token.getToken())
                .retrieve()
                .bodyToFlux(Enterprise.class)
                .collectList()
                .block();
    }

    private Token findCurrentToken() {
        Optional<Token> currentToken =  tokenRepository.findTopByOrderBySaveAtDesc();
        if (currentToken.isPresent()) {
            if (this.calculateSecondsSinceCreation(currentToken.get().getSaveAt()) < 86400)
                return currentToken.get();
        }
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("willy");
        loginRequest.setPassword("password");
        currentToken = Optional.ofNullable(this.login(loginRequest));
        if (currentToken.isEmpty())
            throw new RuntimeException("Failed to authenticate");
        tokenRepository.save(currentToken.get());
        log.info("token = {}", currentToken.get());
        return currentToken.get();
    }

    private Token authentication(JsonNode jsonNode) {
        Token token = new Token();
        token.setToken(jsonNode.get("token").asText());
        token.setRoles(jsonNode.get("roles").asText());
        token.setSaveAt(Instant.now());
        Optional<User> currentUser = securityUtils.getCurrentUser();
        currentUser.ifPresent(user -> token.setUserId(user.getId()));
        token.setId(UUID.randomUUID());
        Token savedToken = tokenRepository.customSave(token);
        log.info("saved token {}", savedToken);
        return savedToken;
    }

    private long calculateSecondsSinceCreation(Instant saveAt) {
        Instant now = Instant.now();
        Duration duration = Duration.between(saveAt, now);
        return duration.getSeconds();
    }
}

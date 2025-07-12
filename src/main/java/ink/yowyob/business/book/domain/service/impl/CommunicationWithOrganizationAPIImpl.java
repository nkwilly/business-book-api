package ink.yowyob.business.book.domain.service.impl;

import ink.yowyob.business.book.domain.exception.AuthenticationException;
import ink.yowyob.business.book.infrastructure.entity.Enterprise;
import ink.yowyob.business.book.infrastructure.entity.User;
import ink.yowyob.business.book.infrastructure.repository.EnterpriseDataRepository;
import ink.yowyob.business.book.infrastructure.repository.TokenRepository;
import ink.yowyob.business.book.infrastructure.repository.UserRepository;
import ink.yowyob.business.book.domain.service.CommunicationWithOrganizationAPI;
import ink.yowyob.business.book.infrastructure.entity.Token;
import ink.yowyob.business.book.presentation.dto.enterprise.CreateEnterpriseDto;
import ink.yowyob.business.book.presentation.dto.enterprise.EnterpriseDto;
import ink.yowyob.business.book.presentation.dto.user.LoginDto;
import ink.yowyob.business.book.presentation.dto.user.RegisterDto;
import ink.yowyob.business.book.presentation.dto.enterprise.UpdateEnterpriseDto;
import ink.yowyob.business.book.utils.GenerateUtils;
import ink.yowyob.business.book.utils.SecurityUtils;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

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

    @Value("${base.url}")
    private String baseUrl;

    @Value("${api.token}")
    private String token;

    @Override
    public Mono<Token> register(RegisterDto RegisterDto) {
        String url = baseUrl + "/auth/register";
        JsonNode jsonNode = webClient.post()
                .uri(url)
                .bodyValue(RegisterDto)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        if (jsonNode == null || jsonNode.get("token") == null) {
            log.error("Failed to register, jsonNode received is {}", jsonNode);
            throw new AuthenticationException("Failed to register");
        }
        return authentication(jsonNode);
    }

    @Override
    public Mono<Token> login(LoginDto LoginDto) {
        String url = baseUrl + "/auth/login";
        JsonNode jsonNode = webClient.post()
                .uri(url)
                .bodyValue(LoginDto)
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
    public Mono<Enterprise> createEnterprise(Enterprise enterprise) {
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
                .bodyToMono(Enterprise.class);
    }

    @Override
    public Mono<Enterprise> updateEnterprise(UpdateEnterpriseDto enterprise) {
        String url = baseUrl + "/organizations/" + enterprise.getOrganizationId();
        //Token token = this.findCurrentToken();
        return webClient.put()
                .uri(url)
                .header("Authorization", "Bearer " + token)
                .bodyValue(enterprise)
                .retrieve()
                .bodyToMono(Enterprise.class);
    }

    @Override
    public Mono<Void> deleteEnterprise(UUID uuid) {
        String url = baseUrl + "/organizations/" + uuid;
        //Token token = this.findCurrentToken();
        webClient.delete()
                .uri(url)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toBodilessEntity()
                .map(res -> res.getStatusCode().is2xxSuccessful())
                .onErrorReturn(false);
        return Mono.empty();
    }

    @Override
    public Mono<Enterprise> getEnterpriseById(UUID id) {
        String url = baseUrl + "/organizations/" + id;
        return webClient.get()
                .uri(url)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(Enterprise.class);
    }

    @Override
    public Flux<Enterprise> getAllEnterprise() {
        String url = baseUrl + "/organizations";
        return webClient.get()
                .uri(url)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToFlux(Enterprise.class);
    }

    private Token findCurrentToken() {
        Mono<Token> currentToken =  tokenRepository.findTopByOrderBySaveAtDesc();
        LoginDto LoginDto = new LoginDto();
        LoginDto.setUsername("willy");
        LoginDto.setPassword("password");
        currentToken = this.login(LoginDto);

        tokenRepository.save(currentToken.block());

        return currentToken.block();
    }

    private Mono<Token> authentication(JsonNode jsonNode) {
        Token token = new Token();
        token.setContent(jsonNode.get("token").asText());
        token.setRoles(jsonNode.get("roles").asText());
        token.setSaveAt(Instant.now());
        Optional<User> currentUser = securityUtils.getCurrentUser();
        currentUser.ifPresent(user -> token.setUserId(user.getId()));
        token.setId(GenerateUtils.generateId());
        Mono<Token> savedToken = tokenRepository.customSave(token);
        log.info("saved token {}", savedToken);
        return savedToken;
    }

    private long calculateSecondsSinceCreation(Instant saveAt) {
        Instant now = Instant.now();
        Duration duration = Duration.between(saveAt, now);
        return duration.getSeconds();
    }
}


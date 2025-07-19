package com.business.book.infrastructure.security;

import com.business.book.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class AuthTokenFilter implements WebFilter {

    private static final Logger log = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return Mono.justOrEmpty(parseJwt(exchange.getRequest()))
                .filter(jwt -> jwtUtils.validateToken(jwt))
                .flatMap(jwt -> {
                    String username = jwtUtils.getUsernameFromToken(jwt);
                    return userDetailsService.findByUsername(username)
                            .map(userDetails -> {
                                Authentication auth = new UsernamePasswordAuthenticationToken(
                                        userDetails, null, userDetails.getAuthorities());
                                return auth;
                            });
                })
                .doOnNext(authentication -> {
                    exchange.getAttributes().put(
                            ReactiveSecurityContextHolder.class.getName(),
                            new SecurityContextImpl(authentication)
                    );
                })
                .then(chain.filter(exchange))
                .onErrorResume(e -> {
                    log.error("Cannot set user authentication: {}", e.getMessage());
                    return chain.filter(exchange);
                });
    }

    private String parseJwt(ServerHttpRequest request) {
        List<String> headers = request.getHeaders().get("Authorization");
        if (headers != null && !headers.isEmpty()) {
            String headerAuth = headers.get(0);
            if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
                return headerAuth.substring(7);
            }
        }
        return null;
    }
}

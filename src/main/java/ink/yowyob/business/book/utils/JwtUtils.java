package ink.yowyob.business.book.utils;

import ink.yowyob.business.book.domain.exception.EntityNotFoundException;
import ink.yowyob.business.book.infrastructure.entity.Role;
import ink.yowyob.business.book.infrastructure.entity.User;
import ink.yowyob.business.book.infrastructure.repository.RoleRepository;
import ink.yowyob.business.book.infrastructure.repository.UserRepository;
import ink.yowyob.business.book.infrastructure.repository.UserRoleRepository;
import ink.yowyob.business.book.infrastructure.security.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Data
@Service
public class JwtUtils {
    private static final Logger log = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int jwtExpirationInMs;

    @Value("${jwt.refresh.expiration}")
    private int jwtRefreshExpirationInMs;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public String generateToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("roles", userPrincipal.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationInMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("roles", userPrincipal.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(( new Date()).getTime() + jwtRefreshExpirationInMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public boolean validateJwtToken(String authToken) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, IllegalArgumentException {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("JWT token expired {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token not supported {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("JWT token malformed {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty {}", e.getMessage());
        }
        return false;
    }

    public String generateJwtTokenFromUserId(UUID userId) {
        User user = userRepository.findById(userId).block();
        if (user == null)
            throw new EntityNotFoundException("User not found");
        List<Role> roles = Objects.requireNonNull(userRoleRepository.findByUserId(user.getId())
                        .collectList()
                        .block())
                .stream().map(userRole -> roleRepository.findById(userRole.getRoleId()))
                .map(Mono::block)
                .toList();
        UserDetailsImpl userPrincipal = UserDetailsImpl.build(user, roles);

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("roles", userPrincipal.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(( new Date()).getTime() + jwtRefreshExpirationInMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }
}

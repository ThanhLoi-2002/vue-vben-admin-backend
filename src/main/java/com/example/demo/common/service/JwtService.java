package com.example.demo.common.service;

import com.example.demo.modules.sys.sys002user.dto.request.UserPayload;
import com.example.demo.modules.sys.sys002user.entities.Sys002user;
import com.example.demo.modules.sys.sys002user.repo.Sys002userRepo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC, makeFinal = true)
public class JwtService {
    @NonFinal
    @Value("${spring.jwt.valid-duration}")
    int tokenTime;

    @NonFinal
    @Value("${spring.jwt.refreshable-duration}")
    int refreshTokenTime;

    @NonFinal
    @Value("${spring.jwt.signerKey}")
    String key;

//    RoleService roleService;
    Sys002userRepo userRepository;

    private Key getKey() {
        return Keys.hmacShaKeyFor(key.getBytes());
    }

    public String generateToken(Sys002user user, int time) {
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + time)
                )
                .signWith(getKey())
                .compact();
    }

    public Claims extractAllClaims(String token, String tokenType) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, Objects.equals(tokenType, "accessToken") ? "expiredAccessToken" : "expiredRefreshToken");
        } catch (JwtException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalidToken");
        }
    }

    public UserPayload getUserByToken(String token, String tokenType) {
        Claims claims = extractAllClaims(token, tokenType);
        Long id = Long.valueOf(claims.getSubject());

//        List<String> roles = roleService.getUserRoles(id);
//        List<String> permissions = roleService.getUserPermissions(id);
        UserPayload userPayload = new UserPayload();
        userPayload.setId(id);
//        userPayload.setRoles(roles);
//        userPayload.setPermissions(permissions);

        return userPayload;
    }
}

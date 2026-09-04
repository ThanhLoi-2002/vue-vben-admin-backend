package com.example.demo.modules.sys.auth.service;

import com.example.demo.common.service.JwtService;
import com.example.demo.modules.sys.auth.dto.request.LoginRequest;
import com.example.demo.modules.sys.auth.dto.request.RegisterRequest;
import com.example.demo.modules.sys.auth.dto.response.LoginResponse;
import com.example.demo.modules.sys.sys002user.dto.request.UserPayload;
import com.example.demo.modules.sys.sys002user.entities.Sys002user;
import com.example.demo.modules.sys.sys002user.repo.Sys002userRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {
    Sys002userRepo sys002userRepo;
    PasswordEncoder passwordEncoder;
    JwtService jwtService;

    public LoginResponse register(RegisterRequest request) {
        Optional<Sys002user> userExisted = sys002userRepo.findByUsername(request.getUsername());

        if (userExisted.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "existed");
        }

        Sys002user user = new Sys002user();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        sys002userRepo.save(user);

        String token = jwtService.generateToken(user, jwtService.tokenTime);
        String refreshToken = jwtService.generateToken(user, jwtService.refreshTokenTime);
        return LoginResponse.builder().accessToken(token).refreshToken(refreshToken).build();
    }

    public LoginResponse login(LoginRequest request) {
        Optional<Sys002user> user = sys002userRepo.findByUsername(request.getUsername());

        if(user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "notFound");
        }

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.get().getPassword()
        )) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalidPassword");
        }

        String token = jwtService.generateToken(user.get(), jwtService.tokenTime);
        String refreshToken = jwtService.generateToken(user.get(), jwtService.refreshTokenTime);

        return LoginResponse.builder().accessToken(token).refreshToken(refreshToken).build();
    }

    public String newToken(String refreshToken) {
        UserPayload user = jwtService.getUserByToken(refreshToken, "refreshToken");

        Sys002user u = sys002userRepo.findById(user.getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "notFound")
        );
        return jwtService.generateToken(u, jwtService.tokenTime);
    }
}

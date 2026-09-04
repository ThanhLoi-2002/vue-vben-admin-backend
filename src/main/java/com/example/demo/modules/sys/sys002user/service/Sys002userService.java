package com.example.demo.modules.sys.sys002user.service;

import com.example.demo.modules.sys.sys002user.entities.Sys002user;
import com.example.demo.modules.sys.sys002user.repo.Sys002userRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Sys002userService {
    Sys002userRepo sys002userRepo;

    public Sys002user findById(Long id) {
        return sys002userRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "notFound"));
    }
}

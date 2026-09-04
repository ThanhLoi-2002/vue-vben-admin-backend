package com.example.demo.modules.sys.sys002user.repo;

import com.example.demo.modules.sys.sys002user.entities.Sys002user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Sys002userRepo extends JpaRepository<Sys002user, Long>, JpaSpecificationExecutor<Sys002user> {
    Optional<Sys002user> findByUsername(String username);
}

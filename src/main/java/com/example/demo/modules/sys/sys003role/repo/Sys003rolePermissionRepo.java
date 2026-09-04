package com.example.demo.modules.sys.sys003role.repo;

import com.example.demo.modules.sys.sys003role.entity.Sys003rolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Sys003rolePermissionRepo
        extends JpaRepository<Sys003rolePermission, Long> {

    void deleteByPermission(String permission);

    List<Sys003rolePermission> findByPermissionIn(List<String> permissions);
}

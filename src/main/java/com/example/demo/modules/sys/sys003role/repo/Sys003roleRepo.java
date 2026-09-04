package com.example.demo.modules.sys.sys003role.repo;

import com.example.demo.modules.sys.sys003role.entity.Sys003role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Sys003roleRepo extends JpaRepository<Sys003role, Long> {
    Sys003role findByName(String name);
    Sys003role findByNameAndIdNot(String name, Long id);
}

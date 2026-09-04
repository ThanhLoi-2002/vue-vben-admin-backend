package com.example.demo.modules.sys.sys003role.repo;

import com.example.demo.modules.sys.sys003role.entity.Sys003userRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Sys003userRoleRepo
        extends JpaRepository<Sys003userRole, Long> {

    void deleteByUserId(Long userId);

    List<Sys003userRole> findByUserId(Long userId);

    @Query("""
        SELECT DISTINCT rp.permission
        FROM Sys003userRole ur,
             Sys003rolePermission rp
        WHERE ur.roleId = rp.roleId
        AND ur.userId = :userId
    """)
    List<String> getPermissions(Long userId);

    @Query("""
        SELECT r.name
        FROM Sys003userRole ur
        JOIN Sys003role r ON ur.roleId = r.id
        WHERE ur.userId = :userId
    """)
    List<String> getRoles(Long userId);
}

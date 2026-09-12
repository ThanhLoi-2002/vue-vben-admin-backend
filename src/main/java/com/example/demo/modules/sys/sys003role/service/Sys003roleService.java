package com.example.demo.modules.sys.sys003role.service;

import com.example.demo.modules.sys.sys001structure.entity.Sys001structure;
import com.example.demo.modules.sys.sys001structure.repo.Sys001structureRepo;
import com.example.demo.modules.sys.sys002user.repo.Sys002userRepo;
import com.example.demo.modules.sys.sys003role.dto.request.AssignRoleRequest;
import com.example.demo.modules.sys.sys003role.dto.request.RoleRequest;
import com.example.demo.modules.sys.sys003role.dto.response.RoleResponse;
import com.example.demo.modules.sys.sys003role.entity.Sys003role;
import com.example.demo.modules.sys.sys003role.entity.Sys003rolePermission;
import com.example.demo.modules.sys.sys003role.entity.Sys003userRole;
import com.example.demo.modules.sys.sys003role.repo.Sys003rolePermissionRepo;
import com.example.demo.modules.sys.sys003role.repo.Sys003roleRepo;
import com.example.demo.modules.sys.sys003role.repo.Sys003userRoleRepo;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class Sys003roleService {
    Sys003roleRepo roleRepo;
    Sys003rolePermissionRepo rolePermissionRepo;
    Sys002userRepo userRepo;
    Sys003userRoleRepo userRoleRepo;
    Sys001structureRepo structureRepo;

    public RoleResponse create(RoleRequest req) {
        Sys003role existed = roleRepo.findByName(req.getName());
        if (existed != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "nameExisted");
        }

        Sys003role role = new Sys003role();
        role.setName(req.getName());
        role.setDescription(req.getDescription());
        role.setModuleId(req.getModuleId());
        roleRepo.save(role);

        return getRoleModule(role);
    }

    private RoleResponse getRoleModule(Sys003role role) {
        Optional<Sys001structure> structure = structureRepo.findById(role.getId());

        RoleResponse res = new RoleResponse(role);
        structure.ifPresent(value -> res.setModule(value.getName()));

        return res;
    }

    public List<RoleResponse> getAll() {
        List<Sys003role> roles = roleRepo.findAll();

        // Lấy danh sách moduleId
        List<Long> moduleIds = roles.stream()
                .map(Sys003role::getModuleId)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        // Query 1 lần
        Map<Long, String> moduleMap = structureRepo.findAllById(moduleIds)
                .stream()
                .collect(Collectors.toMap(Sys001structure::getId, Sys001structure::getName));

        // Map response
        return roles.stream()
                .map(role -> {
                    RoleResponse res = new RoleResponse(role);

                    String module = moduleMap.get(role.getModuleId());
                    if (module != null) {
                        res.setModule(module);
                    }

                    return res;
                })
                .toList();
    }

    public RoleResponse update(Long id, RoleRequest req) {
        Sys003role role = roleRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "roleNotFound"));

        Sys003role existed = roleRepo.findByNameAndIdNot(
                req.getName(),
                id
        );

        if (existed != null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "nameExisted"
            );
        }

        role.setName(req.getName());
        role.setDescription(req.getDescription());
        role.setModuleId(req.getModuleId());
        roleRepo.save(role);

        return getRoleModule(role);
    }

    public void delete(Long id) {
        Sys003role role = roleRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "roleNotFound"));
        roleRepo.delete(role);
    }

    public void setAccess(
            Map<String, List<Long>> req
    ) {
        for (Map.Entry<String, List<Long>> entry : req.entrySet()) {
            String permission = entry.getKey();
            List<Long> roleIds = entry.getValue();

            rolePermissionRepo.deleteByPermission(permission);
            List<Sys003rolePermission> entities =
                    roleIds
                            .stream()
                            .distinct()
                            .map(id -> {

                                Sys003rolePermission rp =
                                        new Sys003rolePermission();

                                rp.setRoleId(id);
                                rp.setPermission(permission);

                                return rp;
                            })
                            .toList();
            rolePermissionRepo.saveAll(entities);
        }
    }

    public Map<String, List<Long>> getAccess(
            List<String> permissions
    ) {
        List<Sys003rolePermission> list = rolePermissionRepo.findByPermissionIn(permissions);
        Map<String, List<Long>> result = new HashMap<>();
        for (Sys003rolePermission item : list) {
            String permission = item.getPermission();
            Long roleId = item.getRoleId();

            result.computeIfAbsent(permission, k -> new ArrayList<>())
                    .add(roleId);
        }

        return result;
    }

    public void assignRoles(
            AssignRoleRequest req
    ) {

        userRepo.findById(req.getId())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "userNotFound"));

        userRoleRepo.deleteByUserId(req.getId());

        List<Sys003userRole> entities =
                req.getRoleIds()
                        .stream()
                        .distinct()
                        .map(roleId -> {

                            Sys003userRole ur = new Sys003userRole();

                            ur.setUserId(req.getId());
                            ur.setRoleId(roleId);

                            return ur;
                        })
                        .toList();

        userRoleRepo.saveAll(entities);
    }

    public List<String> getUserRoles(Long userId) {
        return userRoleRepo.getRoles(userId);
    }

    public List<String> getUserPermissions(Long userId) {
        return userRoleRepo.getPermissions(userId);
    }
}

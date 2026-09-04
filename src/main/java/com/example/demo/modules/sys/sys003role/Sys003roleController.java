package com.example.demo.modules.sys.sys003role;

import com.example.demo.common.filter.UserFilter;
import com.example.demo.modules.sys.sys003role.dto.request.AssignRoleRequest;
import com.example.demo.modules.sys.sys003role.dto.request.RoleRequest;
import com.example.demo.modules.sys.sys003role.dto.response.PermissionTreeResponse;
import com.example.demo.modules.sys.sys003role.dto.response.RoleResponse;
import com.example.demo.modules.sys.sys003role.dto.response.UserRoleResponse;
import com.example.demo.modules.sys.sys003role.service.Sys003permissionService;
import com.example.demo.modules.sys.sys003role.service.Sys003roleService;
import com.example.demo.modules.sys.sys003role.service.Sys003userRoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys003role")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Sys003roleController {
    Sys003roleService roleService;
    Sys003permissionService permissionService;
    Sys003userRoleService userRoleService;

    @PostMapping
    public RoleResponse create(@RequestBody RoleRequest req) {
        return roleService.create(req);
    }

    @PostMapping("set-access")
    public void setAccess(@RequestBody Map<String, List<Long>> req) {
        roleService.setAccess(req);
    }

    @PostMapping("access")
    public Map<String, List<Long>> getAccess(@RequestBody List<String> req) {
        return roleService.getAccess(req);
    }

    @GetMapping
    public List<RoleResponse> getAll() {
        return roleService.getAll();
    }

    @GetMapping("users-roles")
    public Page<UserRoleResponse> getUserRole(@ModelAttribute UserFilter filter) {
        return userRoleService.getUserRole(filter);
    }

    @PutMapping("/{id}")
    public RoleResponse update(@PathVariable Long id, @RequestBody RoleRequest req) {
        return roleService.update(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        roleService.delete(id);
    }

    @GetMapping("/permissions")
    public List<PermissionTreeResponse> getPermissions() {
        return permissionService.getAllPermissions();
    }

    @PutMapping("/assign-roles")
    public void assignRoles(
            @RequestBody AssignRoleRequest req
    ) {
        roleService.assignRoles(req);
    }
}

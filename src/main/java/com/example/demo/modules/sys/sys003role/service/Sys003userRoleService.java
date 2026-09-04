package com.example.demo.modules.sys.sys003role.service;

import com.example.demo.common.filter.UserFilter;
import com.example.demo.modules.sys.sys002user.entities.Sys002user;
import com.example.demo.modules.sys.sys002user.service.Sys002userService;
import com.example.demo.modules.sys.sys003role.dto.response.UserRoleResponse;
import com.example.demo.modules.sys.sys003role.entity.Sys003role;
import com.example.demo.modules.sys.sys003role.entity.Sys003userRole;
import com.example.demo.modules.sys.sys003role.repo.Sys003roleRepo;
import com.example.demo.modules.sys.sys003role.repo.Sys003userRoleRepo;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class Sys003userRoleService {
    Sys003userRoleRepo userRoleRepo;
    Sys003roleRepo roleRepo;
    Sys002userService userService;

    public Page<UserRoleResponse> getUserRole(UserFilter filter) { // Nên đổi kiểu trả về thành Page thay vì List để giữ phân trang ở Controller
        Page<Sys002user> page = userService.findAll(filter);

        return page.map(user -> {
            // Giả sử bạn có UserRoleService hoặc trong User đã có sẵn lấy danh sách role name
            List<Long> roleIds = userRoleRepo.findByUserId(user.getId()).stream().map(Sys003userRole::getRoleId).toList();
            List<String> roles = roleRepo.findAllById(roleIds).stream().map(Sys003role::getName).toList();

            // Trả về constructor bạn đã định nghĩa
            return new UserRoleResponse(user, roles, roleIds);
        });
    }
}

package com.example.demo.modules.sys.sys003role.dto.response;

import com.example.demo.modules.sys.sys002user.dto.response.Sys002userResponse;
import com.example.demo.modules.sys.sys002user.entities.Sys002user;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserRoleResponse {
    Sys002userResponse user;
    List<String> roles;
    List<Long> roleIds;

    public UserRoleResponse(Sys002user u, List<String> roles, List<Long> roleIds) {
        this.user = new Sys002userResponse(u);
        this.roles = roles;
        this.roleIds = roleIds;
    }
}

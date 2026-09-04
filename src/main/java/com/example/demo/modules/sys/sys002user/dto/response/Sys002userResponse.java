package com.example.demo.modules.sys.sys002user.dto.response;

import java.util.List;

import com.example.demo.modules.sys.sys002user.dto.request.UserPayload;
import org.springframework.beans.BeanUtils;

import com.example.demo.common.base.BaseResponse;
import com.example.demo.modules.sys.sys002user.entities.Sys002user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
public class Sys002userResponse extends BaseResponse {
    String username;
    String name;
    String avatar;

    List<String> roles;
    List<String> permissions;

    public Sys002userResponse(Sys002user u, String... relations) {
        super(u, relations);
        BeanUtils.copyProperties(u, this, "createdBy", "updatedBy");
    }
}

package com.example.demo.modules.sys.sys003role.dto.response;

import com.example.demo.modules.sys.sys003role.entity.Sys003role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@AllArgsConstructor
public class RoleResponse {
    Long id;
    String name;
    String description;

    Long moduleId;
    String module;

    public RoleResponse(Sys003role e) {
        BeanUtils.copyProperties(e, this);
    }
}

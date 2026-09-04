package com.example.demo.modules.sys.sys003role.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleRequest {
    String name;
    String description;
    Long moduleId;
}

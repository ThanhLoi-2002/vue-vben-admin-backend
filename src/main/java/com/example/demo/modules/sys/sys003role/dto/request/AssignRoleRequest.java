package com.example.demo.modules.sys.sys003role.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AssignRoleRequest {
    Long id;
    List<Long> roleIds;
}

package com.example.demo.modules.sys.sys003role.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PermissionResponse {
    String group;
    List<String> permissions;
}

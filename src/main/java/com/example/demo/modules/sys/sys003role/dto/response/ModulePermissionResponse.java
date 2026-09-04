package com.example.demo.modules.sys.sys003role.dto.response;

import java.util.List;

public record ModulePermissionResponse(
        String module,
        List<String> permissions
) {
}

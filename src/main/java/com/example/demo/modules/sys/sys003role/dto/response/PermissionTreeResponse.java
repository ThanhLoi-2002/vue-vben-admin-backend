package com.example.demo.modules.sys.sys003role.dto.response;

import java.util.List;

public record PermissionTreeResponse(
        String app,
        List<ModulePermissionResponse> modules
) {
}

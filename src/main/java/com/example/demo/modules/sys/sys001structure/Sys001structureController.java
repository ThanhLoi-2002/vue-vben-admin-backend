package com.example.demo.modules.sys.sys001structure;

import com.example.demo.common.annotation.app.ResponseMessage;
import com.example.demo.common.annotation.currentUser.CurrentUser;
import com.example.demo.common.annotation.permission.RequiresPermission;
import com.example.demo.common.util.PermissionConstant;
import com.example.demo.modules.sys.sys001structure.dto.request.StructureSortRequest;
import com.example.demo.modules.sys.sys001structure.dto.response.StructureResponse;
import com.example.demo.modules.sys.sys001structure.entity.Sys001structure;
import com.example.demo.modules.sys.sys001structure.service.Sys001structureService;
import com.example.demo.modules.sys.sys002user.dto.request.UserPayload;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sys001structure")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Sys001structureController {
    Sys001structureService structureService;

    @GetMapping("/all")
//    @RequiresPermission(PermissionConstant.STRUCTURE.READ)
    public StructureResponse getTree() {
        return structureService.getMenuTree();
    }

    @GetMapping("/trash")
//    @RequiresPermission(PermissionConstant.STRUCTURE.READ)
    public List<StructureResponse> getTrash() {
        return structureService.getTrashMenu().stream().map(StructureResponse::new).toList();
    }

    @GetMapping("/menu-by-user")
    public List<StructureResponse> getMenuByUser(@CurrentUser UserPayload user) {
        return structureService.getMenuByUser(user.getId(), user.getPermissions() != null ? user.getPermissions() : List.of(), user.getRoles());
    }

    @GetMapping("/module")
    public List<StructureResponse> getModule() {
        return structureService.getModule().stream().map(StructureResponse::new).toList();
    }

    @GetMapping("/controller-by-module")
    public List<StructureResponse> getControllersByModule(@RequestParam Long moduleId) {
        return structureService.getControllersByModule(moduleId).stream().map(StructureResponse::new).toList();
    }

    @PostMapping
//    @RequiresPermission(PermissionConstant.STRUCTURE.CREATE_UPDATE)
    public StructureResponse createOrUpdate(@RequestBody Sys001structure structure) {
        return new StructureResponse(structureService.saveOrUpdate(structure));
    }

    @PutMapping("/sort")
    @ResponseMessage("success")
//    @RequiresPermission(PermissionConstant.STRUCTURE.CREATE_UPDATE)
    public void updateSort(@RequestBody List<StructureSortRequest> updates) {
        structureService.updateMenuOrder(updates);
    }
}

package com.example.demo.modules.sys.sys001structure.service;

import com.example.demo.modules.sys.sys001structure.dto.request.StructureRequest;
import com.example.demo.modules.sys.sys001structure.dto.request.StructureSortRequest;
import com.example.demo.modules.sys.sys001structure.dto.response.StructureResponse;
import com.example.demo.modules.sys.sys001structure.entity.Sys001structure;
import com.example.demo.modules.sys.sys001structure.repo.Sys001structureRepo;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class Sys001structureService {
    Sys001structureRepo structureRepository;

    public StructureResponse getMenuTree() {
        List<Sys001structure> allMenus = structureRepository.findBySttOrderBySortAsc(1);

        return buildTree(allMenus);
    }

    private StructureResponse buildTree(List<Sys001structure> menus) {

        Map<Long, StructureResponse> nodeMap = menus.stream()
                .map(StructureResponse::new)
                .collect(Collectors.toMap(
                        StructureResponse::getId,
                        node -> node
                ));

        StructureResponse root = null;

        for (StructureResponse node : nodeMap.values()) {
            if (node.getPid() == null || node.getPid() == 0) {
                root = node;
            } else {
                // parent có tham chiếu với nodeMap
                StructureResponse parent = nodeMap.get(node.getPid());
                if (parent != null) {
                    parent.getChildren().add(node);
                }
            }
        }

        nodeMap.values().forEach(node ->
                node.getChildren().sort(Comparator.comparing(StructureResponse::getSort))
        );

        return root;
    }

    public List<Sys001structure> getTrashMenu() {
        return structureRepository.findByStt(-1);
    }

    public Sys001structure save(Sys001structure structure) {
        structure.setSort(0);
        structure.setAuthCode(structure.getAuthCode() == null ? List.of() : structure.getAuthCode());
        return structureRepository.save(structure);
    }

    public Sys001structure update(Sys001structure structure) {
        Sys001structure e = new Sys001structure();
        BeanUtils.copyProperties(structure, e, "sort");
        return structureRepository.save(e);
    }

    public void updateMenuOrder(List<StructureSortRequest> updates) {
        for (StructureSortRequest dto : updates) {
            structureRepository.updateParentAndSort(dto.getId(), dto.getSort());
        }
    }

    public List<StructureResponse> getMenuByUser(Long userId, List<String> permissions, List<String> roles) {
        List<Sys001structure> menus = structureRepository.findBySttOrderBySortAsc(1);

        List<Sys001structure> menusByPermissions = menus.stream()
                .filter(menu -> {
                    List<String> authCodes = menu.getAuthCode();

                    // Nếu không yêu cầu quyền nào -> Cho phép hiển thị luôn
                    if (authCodes == null || authCodes.isEmpty()) {
                        return true;
                    }

                    // Kiểm tra xem danh sách quyền của User có chứa ít nhất 1 quyền trong authCodes không
                    return authCodes.stream()
                            .anyMatch(perm -> perm != null && permissions.contains(perm.trim()));
                })
                .toList();
        StructureResponse response = buildTree(menusByPermissions);
        return response != null ? response.getChildren() : List.of();
    }

    public List<Sys001structure> getModule() {
        return structureRepository.findBySttAndTypeOrderBySortAsc(1, 2);
    }

    public List<Sys001structure> getControllersByModule(Long moduleId) {
        return structureRepository.findBySttAndPidOrderBySortAsc(1, moduleId);
    }
}

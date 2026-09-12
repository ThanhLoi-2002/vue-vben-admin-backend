package com.example.demo.modules.sys.sys001structure.dto.request;

import com.example.demo.modules.sys.sys001structure.entity.MenuType;
import lombok.Getter;
import java.util.List;

@Getter
public class StructureRequest {
    Long id;
    Long pid;
    String name;
    String icon;
    String layout;
    String description;
    Integer type;
    List<String> authCode;
    String component;
    String path;
    MenuType menuType;
    int stt;
}

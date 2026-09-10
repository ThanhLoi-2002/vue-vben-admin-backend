package com.example.demo.modules.sys.sys001structure.dto.response;

import com.example.demo.modules.sys.sys001structure.entity.MenuType;
import com.example.demo.modules.sys.sys001structure.entity.Sys001structureMeta;
import com.example.demo.modules.sys.sys001structure.entity.Sys001structure;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
public class StructureResponse {
    Long id;
    Long pid;
    String code;
    String name;
    String icon;
    int sort;
    String layout;
    String description;
    Integer type;
    List<String> authCode;
    String component;
    String path;
    MenuType menuType;
    int stt;
    List<StructureResponse> children = new ArrayList<>();

    public StructureResponse(Sys001structure e) {
        BeanUtils.copyProperties(e, this);
    }
}

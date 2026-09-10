package com.example.demo.modules.sys.sys001structure.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Sys001structureMeta {
//    int order;
//    String title;
    Boolean affixTab;
    Boolean keepAlive;
    List<String> authority;
    Boolean menuVisibleWithForbidden;
    String badge;
    String badgeType;
    String badgeVariants;
}
package com.example.demo.common.base;

import com.example.demo.modules.sys.sys002user.dto.response.Sys002userResponse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
public class BaseResponse {
    Long id;
    int stt;

    Long cu;
    Sys002userResponse createdBy;

    LocalDateTime ct;

    Long eu;
    Sys002userResponse updatedBy;

    LocalDateTime et;

    public BaseResponse(BaseEntity e, String... relations) {
        Set<String> rels = relations != null
                ? new HashSet<>(Arrays.asList(relations))
                : Collections.emptySet();

        if (rels.contains("createdBy") && e.getCreatedBy() != null) {
            this.createdBy = new Sys002userResponse(e.getCreatedBy());
        }

        if (rels.contains("updatedBy") && e.getUpdatedBy() != null) {
            this.updatedBy = new Sys002userResponse(e.getUpdatedBy());
        }
    }
}

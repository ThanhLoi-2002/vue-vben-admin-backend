package com.example.demo.modules.sys.sys005lang.dto.response;

import com.example.demo.common.base.BaseResponse;
import com.example.demo.modules.sys.sys005lang.entity.Sys005lang;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.BeanUtils;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LangResponse extends BaseResponse {
    String code;
    String en;
    String vi;
    String cn;
    String tw;

    public LangResponse(Sys005lang e, String... relations) {
        super(e, relations);

        BeanUtils.copyProperties(e, this, "createdBy", "updatedBy");
    }
}

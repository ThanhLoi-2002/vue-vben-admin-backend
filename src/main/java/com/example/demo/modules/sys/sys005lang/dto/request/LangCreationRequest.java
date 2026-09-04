package com.example.demo.modules.sys.sys005lang.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LangCreationRequest {
    @Size(min = 1, message = "CODE_INVALID")
    String code;

    String vi;
    String en;
    String tw;
    String cn;
}


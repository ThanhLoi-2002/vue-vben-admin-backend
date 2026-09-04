package com.example.demo.modules.sys.sys002user.dto.request;

import com.example.demo.modules.sys.sys002user.entities.Sys002user;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.BeanUtils;

import java.util.List;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
public class UserPayload {
    Long id;
    String username;
    String name;

    List<String> roles;
    List<String> permissions;
    int stt;

    public UserPayload(Sys002user u) {
        BeanUtils.copyProperties(u, this);
    }
}

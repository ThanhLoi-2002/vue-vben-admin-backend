package com.example.demo.modules.sys.sys002user.entities;

import com.example.demo.common.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "sys002user")
public class Sys002user extends BaseEntity {
    String username;

    String password;

    String name;

    String avatar;
}

package com.example.demo.modules.sys.sys005lang.entity;

import com.example.demo.common.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "sys005lang")
public class Sys005lang extends BaseEntity {
    @Column(nullable = false, unique = true)
    String code;

    String vi;
    String en;
    String cn;
    String tw;
}

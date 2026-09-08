package com.example.demo.modules.sys.sys001structure.entity;

import com.example.demo.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "sys001structure")
public class Sys001structure extends BaseEntity {
    // ID của menu cha (Nếu là menu gốc cao nhất thì pid = 0 hoặc null)
    @Column(name = "pid")
    Long pid = 0L;

    // Đường dẫn router hoặc mã định danh (ví dụ: '/dashboard', 'sys001structure')
    @Column(name = "code")
    String code;

    @Column(name = "name")
    String name;

    @Column(name = "layout")
    String layout;

    @Column(name = "menu_type")
    @Enumerated(EnumType.STRING)
    MenuType menuType;

    // Tên hiển thị của menu trên giao diện (ví dụ: 'Tổng quan', 'Quản lý người dùng')
    @Column(name = "description")
    String description;

    // Loại menu (0: Root, 1: Group, 2: Module, 99: Page/Action)
    @Column(name = "type")
    Integer type = 99;

    // Thứ tự sắp xếp hiển thị giữa các menu đồng cấp
    @Column(name = "sort")
    Integer sort = 0;

    String authCode;

    String component;

    String path;

    @JdbcTypeCode(SqlTypes.JSON)
    @ColumnTransformer(write = "?")
    Sys001structureMeta meta;
}

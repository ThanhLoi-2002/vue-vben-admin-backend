package com.example.demo.modules.sys.sys003role.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(
        name = "sys003rolepermission",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"role_id", "permission"}
                )
        }
)
public class Sys003rolePermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long roleId;
    String permission;
}

package com.example.demo.modules.sys.sys003role.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sys003userrole")
@Getter
@Setter
public class Sys003userRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long userId;

    Long roleId;
}

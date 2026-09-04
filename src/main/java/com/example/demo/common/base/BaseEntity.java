package com.example.demo.common.base;

import com.example.demo.modules.sys.sys002user.entities.Sys002user;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    Integer stt = 1;   // default 1

    Long cu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cu", insertable = false, updatable = false)
    Sys002user createdBy;

    @CreationTimestamp
    @Column(updatable = false)
    LocalDateTime ct;

    Long eu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eu", insertable = false, updatable = false)
    Sys002user updatedBy;

    @UpdateTimestamp
    LocalDateTime et;
}

package com.example.demo.modules.app.subscriptionPackage.model;

import com.example.demo.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Table(name = "subscription_packages")
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class SubscriptionPackage extends BaseEntity {
    @Column(nullable = false)
    String name; // Tên gói: Free, Gói Tháng, Gói Quý, Gói Năm

    @Column(nullable = false, unique = true)
    String code; // FREE, MONTHLY, QUARTERLY, YEARLY

    @Column(nullable = false)
    BigDecimal price;

    @Column(name = "duration_in_days", nullable = false)
    Integer durationInDays; // 0 = vĩnh viễn/free, 30, 90, 365

    @Column(columnDefinition = "TEXT")
    String description;

    @Column(nullable = false)
    Boolean active = true;
}
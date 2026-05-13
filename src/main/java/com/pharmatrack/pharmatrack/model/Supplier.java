package com.pharmatrack.pharmatrack.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "supplier")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String supplierName;

    @Column(nullable = false)
    private String contactPerson;

    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    private String address;

    private Boolean isActive = true;

    private LocalDateTime createdAt = LocalDateTime.now();
}
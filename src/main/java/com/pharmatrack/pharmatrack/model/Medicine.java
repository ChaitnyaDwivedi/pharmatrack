package com.pharmatrack.pharmatrack.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    private String name;
    private String genericName;
    private String manufacturer;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
    @Enumerated(EnumType.STRING)
    private Category category;
    private Double price;
    private Integer minimumStockThreshold;
    private Integer quantityInStock;
    private LocalDate expiryDate;
    private LocalDate manufacturedDate;

    @Column(unique = true)
    private String batchNumber;

    private Boolean prescriptionRequired;
    private Boolean isActive = true;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

}

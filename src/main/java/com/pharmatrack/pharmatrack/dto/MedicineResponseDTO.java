package com.pharmatrack.pharmatrack.dto;

import com.pharmatrack.pharmatrack.model.Category;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class MedicineResponseDTO {

    private Long id;
    private String name;
    private String genericName;
    private String manufacturer;
    private String supplierName;
    private Category category;
    private Double price;
    private Integer quantityInStock;
    private Integer minimumStockThreshold;
    private LocalDate expiryDate;
    private LocalDate manufacturedDate;
    private String batchNumber;
    private Boolean prescriptionRequired;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
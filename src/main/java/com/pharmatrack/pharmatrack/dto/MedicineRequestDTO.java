package com.pharmatrack.pharmatrack.dto;
import jakarta.validation.constraints.*;
import com.pharmatrack.pharmatrack.model.Category;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MedicineRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Generic name is required")
    private String genericName;

    @NotBlank(message = "Manufacturer is required")
    private String manufacturer;

    private Long supplierId;

    @NotNull(message = "Category is required")
    private Category category;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Double price;

    @NotNull(message = "Stock is required")
    @PositiveOrZero(message = "Stock cannot be negative")
    private Integer quantityInStock;

    @NotNull(message = "Minimum stock threshold required")
    private Integer minimumStockThreshold;

    @NotNull(message = "Expiry date required")
    private LocalDate expiryDate;

    @NotNull(message = "Manufactured date required")
    private LocalDate manufacturedDate;

    @NotBlank(message = "Batch number required")
    private String batchNumber;

    @NotNull(message = "Prescription flag required")
    private Boolean prescriptionRequired;
}
package com.pharmatrack.pharmatrack.util;

import com.pharmatrack.pharmatrack.dto.MedicineResponseDTO;
import com.pharmatrack.pharmatrack.model.Medicine;

public class MedicineMapper {

    public static MedicineResponseDTO toDTO(Medicine medicine) {
        return MedicineResponseDTO.builder()
                .id(medicine.getId())
                .name(medicine.getName())
                .genericName(medicine.getGenericName())
                .manufacturer(medicine.getManufacturer())
                .supplierName(
                        medicine.getSupplier() != null ?
                                medicine.getSupplier().getSupplierName() : null
                )
                .category(medicine.getCategory())
                .price(medicine.getPrice())
                .quantityInStock(medicine.getQuantityInStock())
                .minimumStockThreshold(medicine.getMinimumStockThreshold())
                .expiryDate(medicine.getExpiryDate())
                .manufacturedDate(medicine.getManufacturedDate())
                .batchNumber(medicine.getBatchNumber())
                .prescriptionRequired(medicine.getPrescriptionRequired())
                .isActive(medicine.getIsActive())
                .createdAt(medicine.getCreatedAt())
                .updatedAt(medicine.getUpdatedAt())
                .build();
    }
}
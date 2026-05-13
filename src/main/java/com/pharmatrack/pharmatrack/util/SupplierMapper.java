package com.pharmatrack.pharmatrack.util;

import com.pharmatrack.pharmatrack.dto.SupplierResponseDTO;
import com.pharmatrack.pharmatrack.model.Supplier;

public class SupplierMapper {

    public static SupplierResponseDTO toDTO(Supplier supplier) {
        return SupplierResponseDTO.builder()
                .id(supplier.getId())
                .supplierName(supplier.getSupplierName())
                .contactPerson(supplier.getContactPerson())
                .email(supplier.getEmail())
                .phoneNumber(supplier.getPhoneNumber())
                .address(supplier.getAddress())
                .isActive(supplier.getIsActive())
                .createdAt(supplier.getCreatedAt())
                .build();
    }
}
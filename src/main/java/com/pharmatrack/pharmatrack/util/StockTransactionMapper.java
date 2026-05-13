package com.pharmatrack.pharmatrack.util;

import com.pharmatrack.pharmatrack.dto.StockTransactionResponseDTO;
import com.pharmatrack.pharmatrack.model.StockTransaction;

public class StockTransactionMapper {

    public static StockTransactionResponseDTO toDTO(StockTransaction t) {
        return StockTransactionResponseDTO.builder()
                .id(t.getId())
                .medicineName(t.getMedicine().getName())
                .transactionType(t.getTransactionType())
                .quantity(t.getQuantity())
                .remarks(t.getRemarks())
                .transactedAt(t.getTransactedAt())
                .build();
    }
}
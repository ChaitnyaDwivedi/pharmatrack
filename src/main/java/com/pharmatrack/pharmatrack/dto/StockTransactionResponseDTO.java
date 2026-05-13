package com.pharmatrack.pharmatrack.dto;

import com.pharmatrack.pharmatrack.model.TransactionType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class StockTransactionResponseDTO {

    private Long id;
    private String medicineName;
    private TransactionType transactionType;
    private Integer quantity;
    private String remarks;
    private LocalDateTime transactedAt;
}
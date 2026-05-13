package com.pharmatrack.pharmatrack.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class StockTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="medicine_id")
    private Medicine medicine;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private Integer quantity;
    private String remarks;
    private LocalDateTime transactedAt=LocalDateTime.now();
}

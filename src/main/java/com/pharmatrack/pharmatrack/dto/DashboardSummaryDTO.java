package com.pharmatrack.pharmatrack.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardSummaryDTO {

    private long totalMedicines;

    private long totalSuppliers;

    private long lowStockCount;

    private long expiredCount;
}
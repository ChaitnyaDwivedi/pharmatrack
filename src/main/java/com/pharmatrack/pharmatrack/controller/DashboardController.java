package com.pharmatrack.pharmatrack.controller;

import com.pharmatrack.pharmatrack.dto.DashboardSummaryDTO;
import com.pharmatrack.pharmatrack.dto.MedicineResponseDTO;

import com.pharmatrack.pharmatrack.service.DashboardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard API", description = "Inventory insights")
public class DashboardController {

    private final DashboardService dashboardService;

    @Operation(summary = "Dashboard summary")
    @GetMapping("/summary")
    public DashboardSummaryDTO getSummary() {

        return dashboardService.getSummary();
    }

    @Operation(summary = "Stock overview")
    @GetMapping("/stock-overview")
    public Map<String, Integer> getStockOverview() {

        return dashboardService.getStockOverview();
    }

    @Operation(summary = "Expiry alerts")
    @GetMapping("/expiry-alerts")
    public List<MedicineResponseDTO> getExpiryAlerts() {

        return dashboardService.getExpiryAlerts();
    }
}
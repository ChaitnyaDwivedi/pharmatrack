package com.pharmatrack.pharmatrack.service;

import com.pharmatrack.pharmatrack.dto.*;

import java.util.List;
import java.util.Map;

public interface DashboardService {

    DashboardSummaryDTO getSummary();

    Map<String, Integer> getStockOverview();

    List<MedicineResponseDTO> getExpiryAlerts();
}
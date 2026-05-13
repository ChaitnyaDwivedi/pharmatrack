package com.pharmatrack.pharmatrack.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pharmatrack.pharmatrack.dto.DashboardSummaryDTO;
import com.pharmatrack.pharmatrack.dto.MedicineResponseDTO;
import com.pharmatrack.pharmatrack.service.DashboardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DashboardController.class)
class DashboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DashboardService dashboardService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetDashboardSummarySuccessfully() throws Exception {

        DashboardSummaryDTO summary = DashboardSummaryDTO.builder().totalMedicines(10L).totalSuppliers(5L).lowStockCount(2L).expiredCount(1L).build();

        when(dashboardService.getSummary()).thenReturn(summary);

        mockMvc.perform(get("/api/v1/dashboard/summary").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.totalMedicines").value(10)).andExpect(jsonPath("$.totalSuppliers").value(5));
    }

    @Test
    void shouldGetStockOverviewSuccessfully() throws Exception {

        when(dashboardService.getStockOverview()).thenReturn(Map.of("TABLET", 100));
        mockMvc.perform(get("/api/v1/dashboard/stock-overview")).andExpect(status().isOk()).andExpect(jsonPath("$.TABLET").value(100));
    }

    @Test
    void shouldGetExpiryAlertsSuccessfully() throws Exception {

        MedicineResponseDTO medicine = MedicineResponseDTO.builder().id(1L).name("Paracetamol").build();

        when(dashboardService.getExpiryAlerts()).thenReturn(List.of(medicine));

        mockMvc.perform(get("/api/v1/dashboard/expiry-alerts")).andExpect(status().isOk()).andExpect(jsonPath("$[0].name").value("Paracetamol"));
    }
}
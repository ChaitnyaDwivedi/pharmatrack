package com.pharmatrack.pharmatrack.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pharmatrack.pharmatrack.dto.SupplierResponseDTO;
import com.pharmatrack.pharmatrack.service.SupplierService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SupplierController.class)
class SupplierControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SupplierService supplierService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetAllSuppliersSuccessfully() throws Exception {

        SupplierResponseDTO supplier = SupplierResponseDTO.builder().id(1L).supplierName("ABC Suppliers").contactPerson("John").build();

        when(supplierService.getAllSuppliers(0, 5)).thenReturn(List.of(supplier));

        mockMvc.perform(get("/api/v1/suppliers").param("page", "0").param("size", "5").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(1)).andExpect(jsonPath("$[0].supplierName").value("ABC Suppliers"));
    }

    @Test
    void shouldGetSupplierByIdSuccessfully() throws Exception {

        SupplierResponseDTO supplier = SupplierResponseDTO.builder().id(1L).supplierName("ABC Suppliers").contactPerson("John").build();

        when(supplierService.getSupplierById(1L)).thenReturn(supplier);
        mockMvc.perform(get("/api/v1/suppliers/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.supplierName").value("ABC Suppliers"));
    }

    @Test
    void shouldSearchSupplierSuccessfully() throws Exception {

        SupplierResponseDTO supplier = SupplierResponseDTO.builder().id(1L).supplierName("ABC Suppliers").contactPerson("John").build();

        when(supplierService.searchSupplier("ABC")).thenReturn(List.of(supplier));

        mockMvc.perform(get("/api/v1/suppliers/search").param("name", "ABC").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$[0].supplierName").value("ABC Suppliers"));
    }

    @Test
    void shouldActivateSupplierSuccessfully() throws Exception {

        SupplierResponseDTO supplier = SupplierResponseDTO.builder().id(1L).supplierName("ABC Suppliers").build();

        when(supplierService.activateSupplier(1L)).thenReturn(supplier);

        mockMvc.perform(patch("/api/v1/suppliers/1/activate")).andExpect(status().isOk());
    }

    @Test
    void shouldDeactivateSupplierSuccessfully() throws Exception {

        SupplierResponseDTO supplier = SupplierResponseDTO.builder().id(1L).supplierName("ABC Suppliers").build();

        when(supplierService.deactivateSupplier(1L)).thenReturn(supplier);

        mockMvc.perform(patch("/api/v1/suppliers/1/deactivate")).andExpect(status().isOk());
    }

    @Test
    void shouldDeleteSupplierSuccessfully() throws Exception {

        mockMvc.perform(delete("/api/v1/suppliers/1")).andExpect(status().isOk());
    }
}
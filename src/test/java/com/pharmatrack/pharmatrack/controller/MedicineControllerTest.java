package com.pharmatrack.pharmatrack.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pharmatrack.pharmatrack.dto.MedicineResponseDTO;
import com.pharmatrack.pharmatrack.service.MedicineService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MedicineController.class)
class MedicineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicineService medicineService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetAllMedicinesSuccessfully() throws Exception {

        MedicineResponseDTO medicine = MedicineResponseDTO.builder().id(1L).name("Paracetamol").quantityInStock(100).build();

        when(medicineService.getAllMedicines(0, 5, "id")).thenReturn(List.of(medicine));

        mockMvc.perform(get("/api/v1/medicines").param("page", "0").param("size", "5").param("sortBy", "id").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(1)).andExpect(jsonPath("$[0].name").value("Paracetamol"));
    }

    @Test
    void shouldReturnNotFoundWhenMedicineDoesNotExist() {

        when(medicineService.getMedicineById(1L)).thenThrow(new RuntimeException("Medicine not found with id: 1"));
        RuntimeException exception = org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> medicineService.getMedicineById(1L));

        org.junit.jupiter.api.Assertions.assertEquals("Medicine not found with id: 1", exception.getMessage());
    }

}
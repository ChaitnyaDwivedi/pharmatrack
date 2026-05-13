package com.pharmatrack.pharmatrack.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.pharmatrack.pharmatrack.dto.MedicineRequestDTO;

import com.pharmatrack.pharmatrack.exception.InvalidQuantityException;
import com.pharmatrack.pharmatrack.exception.MedicineNotFoundException;
import com.pharmatrack.pharmatrack.exception.OutOfStockException;

import com.pharmatrack.pharmatrack.service.MedicineService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MedicineController.class)
class MedicineControllerNegativeTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicineService medicineService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnNotFoundWhenMedicineDoesNotExist() throws Exception {

        when(medicineService.getMedicineById(1L)).thenThrow(new MedicineNotFoundException("Medicine not found"));

        mockMvc.perform(get("/api/v1/medicines/1")).andExpect(status().isNotFound())

                .andExpect(jsonPath("$.message").value("Medicine not found"));
    }

    @Test
    void shouldReturnBadRequestForInvalidQuantity() throws Exception {

        when(medicineService.restockMedicine(any(Long.class), any(Integer.class))).thenThrow(new InvalidQuantityException("Invalid quantity"));

        mockMvc.perform(patch("/api/v1/medicines/1/restock").param("quantity", "-5")).andExpect(status().isBadRequest())

                .andExpect(jsonPath("$.message").value("Invalid quantity"));
    }

    @Test
    void shouldReturnConflictWhenOutOfStock() throws Exception {

        when(medicineService.dispenseMedicine(any(Long.class), any(Integer.class))).thenThrow(new OutOfStockException("Out of stock"));

        mockMvc.perform(patch("/api/v1/medicines/1/dispense").param("quantity", "100")).andExpect(status().isConflict())

                .andExpect(jsonPath("$.message").value("Out of stock"));
    }

    @Test
    void shouldReturnValidationErrorForInvalidInput() throws Exception {

        MedicineRequestDTO dto = new MedicineRequestDTO();

        dto.setName("");

        mockMvc.perform(post("/api/v1/medicines").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dto))).andExpect(status().isBadRequest());
    }
}
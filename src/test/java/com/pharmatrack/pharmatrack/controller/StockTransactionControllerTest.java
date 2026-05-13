package com.pharmatrack.pharmatrack.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pharmatrack.pharmatrack.dto.StockTransactionResponseDTO;
import com.pharmatrack.pharmatrack.model.TransactionType;
import com.pharmatrack.pharmatrack.service.StockTransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StockTransactionController.class)
class StockTransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockTransactionService stockTransactionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetAllTransactionsSuccessfully() throws Exception {

        StockTransactionResponseDTO transaction = StockTransactionResponseDTO.builder().id(1L).medicineName("Paracetamol").quantity(10).transactionType(TransactionType.RESTOCK).transactedAt(LocalDateTime.now()).build();

        when(stockTransactionService.getAllTransactions()).thenReturn(List.of(transaction));

        mockMvc.perform(get("/api/v1/transactions").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$[0].medicineName").value("Paracetamol"));
    }

    @Test
    void shouldGetTransactionByIdSuccessfully() throws Exception {

        StockTransactionResponseDTO transaction = StockTransactionResponseDTO.builder().id(1L).medicineName("Paracetamol").quantity(10).transactionType(TransactionType.RESTOCK).build();

        when(stockTransactionService.getTransactionById(1L)).thenReturn(transaction);
        mockMvc.perform(get("/api/v1/transactions/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.medicineName").value("Paracetamol"));
    }

    @Test
    void shouldGetTransactionsByMedicineSuccessfully() throws Exception {

        StockTransactionResponseDTO transaction = StockTransactionResponseDTO.builder().id(1L).medicineName("Paracetamol").build();

        when(stockTransactionService.getByMedicine(1L)).thenReturn(List.of(transaction));
        mockMvc.perform(get("/api/v1/transactions/medicine/1")).andExpect(status().isOk()).andExpect(jsonPath("$[0].medicineName").value("Paracetamol"));
    }

    @Test
    void shouldGetTransactionsByTypeSuccessfully() throws Exception {

        StockTransactionResponseDTO transaction = StockTransactionResponseDTO.builder().id(1L).medicineName("Paracetamol").transactionType(TransactionType.RESTOCK).build();

        when(stockTransactionService.getTransactionsByType("RESTOCK")).thenReturn(List.of(transaction));
        mockMvc.perform(get("/api/v1/transactions/type/RESTOCK")).andExpect(status().isOk()).andExpect(jsonPath("$[0].medicineName").value("Paracetamol"));
    }
}
package com.pharmatrack.pharmatrack.service;

import com.pharmatrack.pharmatrack.dto.StockTransactionResponseDTO;
import java.time.LocalDateTime;
import java.util.List;

public interface StockTransactionService {

    List<StockTransactionResponseDTO> getAllTransactions();

    List<StockTransactionResponseDTO> getByMedicine(Long medicineId);

    StockTransactionResponseDTO getTransactionById(Long id);

    List<StockTransactionResponseDTO> getTransactionsByType(String transactionType);

    List<StockTransactionResponseDTO> getTransactionsByDateRange(LocalDateTime from, LocalDateTime to);

}
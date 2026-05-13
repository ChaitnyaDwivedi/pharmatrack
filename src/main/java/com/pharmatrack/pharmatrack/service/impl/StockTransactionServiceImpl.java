package com.pharmatrack.pharmatrack.service.impl;
import com.pharmatrack.pharmatrack.dto.StockTransactionResponseDTO;
import com.pharmatrack.pharmatrack.model.StockTransaction;
import com.pharmatrack.pharmatrack.model.TransactionType;
import com.pharmatrack.pharmatrack.repository.StockTransactionRepository;
import com.pharmatrack.pharmatrack.service.StockTransactionService;
import com.pharmatrack.pharmatrack.util.StockTransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockTransactionServiceImpl implements StockTransactionService {

    private final StockTransactionRepository repository;

    @Override
    public List<StockTransactionResponseDTO> getAllTransactions() {

        return repository.findAll().stream().map(StockTransactionMapper::toDTO).toList();
    }

    @Override
    public List<StockTransactionResponseDTO> getByMedicine(Long medicineId) {

        return repository.findByMedicineId(medicineId).stream().map(StockTransactionMapper::toDTO).toList();
    }

    @Override
    public StockTransactionResponseDTO getTransactionById(Long id) {

        StockTransaction transaction = repository.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));

        return StockTransactionMapper.toDTO(transaction);
    }

    @Override
    public List<StockTransactionResponseDTO> getTransactionsByType(String transactionType) {

        return repository.findByTransactionType(TransactionType.valueOf(transactionType.toUpperCase())).stream().map(StockTransactionMapper::toDTO).toList();
    }

    @Override
    public List<StockTransactionResponseDTO> getTransactionsByDateRange(LocalDateTime from, LocalDateTime to) {

        return repository.findByTransactedAtBetween(from, to).stream().map(StockTransactionMapper::toDTO).toList();
    }

}

package com.pharmatrack.pharmatrack.repository;

import com.pharmatrack.pharmatrack.model.StockTransaction;
import com.pharmatrack.pharmatrack.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface StockTransactionRepository extends JpaRepository<StockTransaction, Long> {

    List<StockTransaction> findByMedicineId(Long medicineId);

    List<StockTransaction> findByTransactionType(TransactionType type);

    List<StockTransaction> findByTransactedAtBetween(LocalDateTime start, LocalDateTime end);

}
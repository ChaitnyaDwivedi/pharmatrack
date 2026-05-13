package com.pharmatrack.pharmatrack.service;

import com.pharmatrack.pharmatrack.dto.StockTransactionResponseDTO;
import com.pharmatrack.pharmatrack.model.Medicine;
import com.pharmatrack.pharmatrack.model.StockTransaction;
import com.pharmatrack.pharmatrack.model.TransactionType;
import com.pharmatrack.pharmatrack.repository.StockTransactionRepository;
import com.pharmatrack.pharmatrack.service.impl.StockTransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StockTransactionServiceImplTest {

    @Mock
    private StockTransactionRepository repository;

    @InjectMocks
    private StockTransactionServiceImpl service;

    private StockTransaction transaction;

    @BeforeEach
    void setUp() {

        Medicine medicine = new Medicine();
        medicine.setId(1L);
        medicine.setName("Paracetamol");
        transaction = new StockTransaction();
        transaction.setId(1L);
        transaction.setMedicine(medicine);
        transaction.setQuantity(10);
        transaction.setTransactionType(TransactionType.RESTOCK);
        transaction.setRemarks("Restocked");
        transaction.setTransactedAt(LocalDateTime.now());
    }

    @Test
    void shouldGetAllTransactionsSuccessfully() {

        when(repository.findAll()).thenReturn(List.of(transaction));
        List<StockTransactionResponseDTO> response = service.getAllTransactions();
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void shouldGetTransactionByIdSuccessfully() {

        when(repository.findById(1L)).thenReturn(Optional.of(transaction));
        StockTransactionResponseDTO response = service.getTransactionById(1L);
        assertNotNull(response);
        assertEquals(TransactionType.RESTOCK, response.getTransactionType());
    }

    @Test
    void shouldThrowExceptionWhenTransactionNotFound() {

        when(repository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class,
                        () -> service.getTransactionById(1L));
        assertTrue(exception.getMessage().contains("Transaction not found"));
    }

    @Test
    void shouldGetTransactionsByMedicineSuccessfully() {

        when(repository.findByMedicineId(1L)).thenReturn(List.of(transaction));
        List<StockTransactionResponseDTO> response = service.getByMedicine(1L);
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
    }

    @Test
    void shouldGetTransactionsByTypeSuccessfully() {

        when(repository.findByTransactionType(TransactionType.RESTOCK)).thenReturn(List.of(transaction));
        List<StockTransactionResponseDTO> response = service.getTransactionsByType("RESTOCK");
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
    }

    @Test
    void shouldGetTransactionsByDateRangeSuccessfully() {

        LocalDateTime from = LocalDateTime.now().minusDays(5);
        LocalDateTime to = LocalDateTime.now();

        when(repository.findByTransactedAtBetween(from, to)).thenReturn(List.of(transaction));
        List<StockTransactionResponseDTO> response = service.getTransactionsByDateRange(from, to);
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
    }
}
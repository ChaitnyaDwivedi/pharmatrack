package com.pharmatrack.pharmatrack.controller;
import com.pharmatrack.pharmatrack.dto.StockTransactionResponseDTO;
import com.pharmatrack.pharmatrack.service.StockTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
@RestController
@RequestMapping("api/v1/transactions")
@RequiredArgsConstructor
@Tag(name="Transaction API",description="Track stock movements")
public class StockTransactionController {
    private final StockTransactionService service;

    @GetMapping
    public List<StockTransactionResponseDTO>getAll(){
        return service.getAllTransactions();
    }
    @GetMapping("/medicine/{id}")
    public List<StockTransactionResponseDTO> getByMedicine(@PathVariable Long id){
        return service.getByMedicine(id);
    }
    @Operation(summary = "Get transaction by id")
    @GetMapping("/{id}")
    public StockTransactionResponseDTO getTransactionById(
            @PathVariable Long id
    ) {

        return service.getTransactionById(id);
    }

    @Operation(summary = "Get transactions by type")
    @GetMapping("/type/{transactionType}")
    public List<StockTransactionResponseDTO> getTransactionsByType(
            @PathVariable String transactionType
    ) {

        return service.getTransactionsByType(transactionType);
    }

    @Operation(summary = "Get transactions by date range")
    @GetMapping("/date-range")
    public List<StockTransactionResponseDTO> getTransactionsByDateRange(

            @RequestParam LocalDateTime from,

            @RequestParam LocalDateTime to
    ) {

        return service.getTransactionsByDateRange(from, to);
    }
}

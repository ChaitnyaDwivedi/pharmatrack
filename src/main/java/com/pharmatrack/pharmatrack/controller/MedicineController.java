package com.pharmatrack.pharmatrack.controller;

import com.pharmatrack.pharmatrack.dto.MedicineRequestDTO;
import com.pharmatrack.pharmatrack.dto.MedicineResponseDTO;
import com.pharmatrack.pharmatrack.service.MedicineService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/v1/medicines")
@RequiredArgsConstructor
@Tag(name = "Medicine API", description = "Manage medicines")
public class MedicineController {

    private final MedicineService medicineService;

    @Operation(summary = "Add a new medicine")
    @PostMapping
    public MedicineResponseDTO addMedicine(@Valid @RequestBody MedicineRequestDTO dto) {
        return medicineService.addMedicine(dto);
    }

    @Operation(summary = "Get all medicines with pagination and sorting")
    @GetMapping
    public List<MedicineResponseDTO> getAllMedicines(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "name") String sortBy
    ) {

        return medicineService.getAllMedicines(page, size, sortBy);
    }

    @Operation(summary = "Get medicine by ID")
    @GetMapping("/{id}")
    public MedicineResponseDTO getMedicine(@PathVariable Long id) {
        return medicineService.getMedicineById(id);
    }

    @Operation(summary = "Search medicines")
    @GetMapping("/search")
    public List<MedicineResponseDTO> search(@RequestParam String name) {
        return medicineService.searchMedicines(name);
    }

    @Operation(summary = "Restock medicine")
    @PatchMapping("/{id}/restock")
    public MedicineResponseDTO restock(@PathVariable Long id,
                                       @RequestParam Integer quantity) {
        return medicineService.restockMedicine(id, quantity);
    }

    @Operation(summary = "Dispense medicine")
    @PatchMapping("/{id}/dispense")
    public MedicineResponseDTO dispense(@PathVariable Long id,
                                        @RequestParam Integer quantity) {
        return medicineService.dispenseMedicine(id, quantity);
    }

    @Operation(summary = "Delete medicine")
    @DeleteMapping("/{id}")
    public void deleteMedicine(@PathVariable Long id) {
        medicineService.deleteMedicine(id);
    }

    @Operation(summary = "Update medicine")
    @PutMapping("/{id}")
    public MedicineResponseDTO updateMedicine(
            @PathVariable Long id,
            @Valid @RequestBody MedicineRequestDTO dto
    ) {
        return medicineService.updateMedicine(id, dto);
    }

    @Operation(summary = "Get medicines by category")
    @GetMapping("/category/{category}")
    public List<MedicineResponseDTO> getByCategory(
            @PathVariable String category
    ) {
        return medicineService.getMedicinesByCategory(category);
    }

    @Operation(summary = "Get medicines by supplier")
    @GetMapping("/supplier/{supplierId}")
    public List<MedicineResponseDTO> getBySupplier(
            @PathVariable Long supplierId
    ) {
        return medicineService.getMedicinesBySupplier(supplierId);
    }

    @Operation(summary = "Get expired medicines")
    @GetMapping("/expired")
    public List<MedicineResponseDTO> getExpiredMedicines() {
        return medicineService.getExpiredMedicines();
    }

    @Operation(summary = "Get low stock medicines")
    @GetMapping("/low-stock")
    public List<MedicineResponseDTO> getLowStockMedicines() {

        return medicineService.getLowStockMedicines();
    }

    @Operation(summary = "Get medicines expiring soon")
    @GetMapping("/expiring-soon")
    public List<MedicineResponseDTO> getExpiringSoonMedicines(
            @RequestParam(defaultValue = "30") int days
    ) {

        return medicineService.getExpiringSoonMedicines(days);
    }

    @Operation(summary = "Get prescription required medicines")
    @GetMapping("/prescription-required")
    public List<MedicineResponseDTO> getPrescriptionRequiredMedicines() {

        return medicineService.getPrescriptionRequiredMedicines();
    }

    @Operation(summary = "Update medicine price")
    @PatchMapping("/{id}/price")
    public MedicineResponseDTO updatePrice(
            @PathVariable Long id,
            @RequestParam Double newPrice
    ) {

        return medicineService.updateMedicinePrice(id, newPrice);
    }

    @Operation(summary = "Deactivate medicine")
    @PatchMapping("/{id}/deactivate")
    public MedicineResponseDTO deactivateMedicine(
            @PathVariable Long id
    ) {

        return medicineService.deactivateMedicine(id);
    }

    @Operation(summary = "Delete expired medicines")
    @DeleteMapping("/expired")
    public void deleteExpiredMedicines() {

        medicineService.deleteExpiredMedicines();
    }
}
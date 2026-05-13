package com.pharmatrack.pharmatrack.controller;

import com.pharmatrack.pharmatrack.dto.MedicineResponseDTO;
import com.pharmatrack.pharmatrack.dto.SupplierRequestDTO;
import com.pharmatrack.pharmatrack.dto.SupplierResponseDTO;

import com.pharmatrack.pharmatrack.service.SupplierService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/suppliers")
@RequiredArgsConstructor
@Tag(name = "Supplier API", description = "Manage suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    @Operation(summary = "Add supplier")
    @PostMapping
    public SupplierResponseDTO addSupplier(
            @Valid @RequestBody SupplierRequestDTO dto
    ) {

        return supplierService.addSupplier(dto);
    }

    @Operation(summary = "Get all suppliers")
    @GetMapping
    public List<SupplierResponseDTO> getAllSuppliers(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size
    ) {

        return supplierService.getAllSuppliers(page, size);
    }

    @Operation(summary = "Get supplier by id")
    @GetMapping("/{id}")
    public SupplierResponseDTO getSupplier(
            @PathVariable Long id
    ) {

        return supplierService.getSupplierById(id);
    }

    @Operation(summary = "Search supplier")
    @GetMapping("/search")
    public List<SupplierResponseDTO> search(
            @RequestParam String name
    ) {

        return supplierService.searchSupplier(name);
    }

    @Operation(summary = "Activate supplier")
    @PatchMapping("/{id}/activate")
    public SupplierResponseDTO activate(
            @PathVariable Long id
    ) {

        return supplierService.activateSupplier(id);
    }

    @Operation(summary = "Deactivate supplier")
    @PatchMapping("/{id}/deactivate")
    public SupplierResponseDTO deactivate(
            @PathVariable Long id
    ) {

        return supplierService.deactivateSupplier(id);
    }

    @Operation(summary = "Update supplier")
    @PutMapping("/{id}")
    public SupplierResponseDTO updateSupplier(

            @PathVariable Long id,

            @Valid @RequestBody SupplierRequestDTO dto
    ) {

        return supplierService.updateSupplier(id, dto);
    }

    @Operation(summary = "Get medicines by supplier")
    @GetMapping("/{id}/medicines")
    public List<MedicineResponseDTO> getMedicinesBySupplier(
            @PathVariable Long id
    ) {

        return supplierService.getMedicinesBySupplier(id);
    }

    @Operation(summary = "Delete supplier")
    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id
    ) {

        supplierService.deleteSupplier(id);
    }
}
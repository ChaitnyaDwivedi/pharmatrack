package com.pharmatrack.pharmatrack.service;

import com.pharmatrack.pharmatrack.dto.MedicineRequestDTO;
import com.pharmatrack.pharmatrack.dto.MedicineResponseDTO;
import com.pharmatrack.pharmatrack.model.Medicine;
import com.pharmatrack.pharmatrack.repository.MedicineRepository;
import com.pharmatrack.pharmatrack.repository.StockTransactionRepository;
import com.pharmatrack.pharmatrack.repository.SupplierRepository;
import com.pharmatrack.pharmatrack.service.impl.MedicineServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.pharmatrack.pharmatrack.exception.MedicineNotFoundException;
import static org.mockito.ArgumentMatchers.anyList;
import com.pharmatrack.pharmatrack.exception.InvalidQuantityException;


import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class MedicineServiceImplTest {

    @Mock
    private MedicineRepository medicineRepository;

    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private StockTransactionRepository stockTransactionRepository;

    @InjectMocks
    private MedicineServiceImpl medicineService;

    private Medicine medicine;

    private MedicineRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        medicine = new Medicine();
        medicine.setId(1L);
        medicine.setName("Paracetamol");
        medicine.setManufacturer("ABC Pharma");
        medicine.setPrice(50.0);
        medicine.setQuantityInStock(100);
        medicine.setMinimumStockThreshold(10);
        medicine.setExpiryDate(LocalDate.now().plusMonths(6));
        requestDTO = new MedicineRequestDTO();
        requestDTO.setName("Paracetamol");
        requestDTO.setManufacturer("ABC Pharma");
        requestDTO.setPrice(50.0);
        requestDTO.setQuantityInStock(100);
        requestDTO.setMinimumStockThreshold(10);
        requestDTO.setExpiryDate(LocalDate.now().plusMonths(6));
    }
    @Test
    void shouldAddMedicineSuccessfully(){
        when(medicineRepository.save(any(Medicine.class))).thenReturn(medicine);
        MedicineResponseDTO response = medicineService.addMedicine(requestDTO);
        assertNotNull(response);
        assertEquals("Paracetamol",response.getName());
        assertEquals(100, response.getQuantityInStock());
        verify(medicineRepository, times(1)).save(any(Medicine.class));
    }
    @Test
    void shouldGetMedicineByIdSuccessfully(){
        when(medicineRepository.findById(1L)).thenReturn(java.util.Optional.of(medicine));
        MedicineResponseDTO response = medicineService.getMedicineById(1L);
        assertNotNull(response);
        assertEquals(1L,response.getId());
        assertEquals("Paracetamol",response.getName());
        verify(medicineRepository, times(1)).findById(1L);
    }
    @Test
    void shouldThrowExceptionWhenMedicineNotFound() {when(medicineRepository.findById(1L))
            .thenReturn(java.util.Optional.empty());RuntimeException exception =
                assertThrows(RuntimeException.class, () -> medicineService.getMedicineById(1L));
        assertEquals("Medicine not found with id: 1", exception.getMessage());
        verify(medicineRepository, times(1)).findById(1L);
    }
    @Test
    void shouldRestockMedicineSuccessfully() {

        when(medicineRepository.findById(1L)).thenReturn(java.util.Optional.of(medicine));
        when(medicineRepository.save(any(Medicine.class))).thenReturn(medicine);
        MedicineResponseDTO response = medicineService.restockMedicine(1L, 50);
        assertNotNull(response);
        verify(medicineRepository, times(1)).findById(1L);
        verify(medicineRepository, times(1)).save(any(Medicine.class));
    }
    @Test
    void shouldThrowExceptionWhenStockIsInsufficient() {

        medicine.setQuantityInStock(5);
        when(medicineRepository.findById(1L)).thenReturn(java.util.Optional.of(medicine));
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> medicineService.dispenseMedicine(1L, 10));
        assertTrue(exception.getMessage().contains("Insufficient stock"));
        verify(medicineRepository, times(1)).findById(1L);
        verify(medicineRepository, never()).save(any(Medicine.class));
    }
    @Test
    void shouldDispenseMedicineSuccessfully() {

        medicine.setQuantityInStock(100);
        when(medicineRepository.findById(1L)).thenReturn(java.util.Optional.of(medicine));
        when(medicineRepository.save(any(Medicine.class))).thenReturn(medicine);
        MedicineResponseDTO response = medicineService.dispenseMedicine(1L, 10);
        assertNotNull(response);
        verify(medicineRepository, times(1)).findById(1L);
        verify(medicineRepository, times(1)).save(any(Medicine.class));
    }
    @Test
    void shouldThrowExceptionWhenMedicineIsExpired() {

        medicine.setExpiryDate(LocalDate.now().minusDays(1));
        when(medicineRepository.findById(1L)).thenReturn(java.util.Optional.of(medicine));
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> medicineService.dispenseMedicine(1L, 5));
        assertTrue(exception.getMessage().contains("expired"));
        verify(medicineRepository, times(1)).findById(1L);
        verify(medicineRepository, never()).save(any(Medicine.class));
    }
    @Test
    void shouldUpdateMedicineSuccessfully() {

        MedicineRequestDTO dto = new MedicineRequestDTO();
        dto.setName("Updated Medicine");
        when(medicineRepository.findById(1L)).thenReturn(Optional.of(medicine));
        when(medicineRepository.save(any(Medicine.class))).thenReturn(medicine);
        MedicineResponseDTO response = medicineService.updateMedicine(1L, dto);
        assertNotNull(response);
        verify(medicineRepository, times(1)).save(any(Medicine.class));
    }

    @Test
    void shouldDeleteMedicineSuccessfully() {

        when(medicineRepository.findById(1L)).thenReturn(Optional.of(medicine));
        doNothing().when(medicineRepository).delete(medicine);
        medicineService.deleteMedicine(1L);
        verify(medicineRepository, times(1)).findById(1L);
        verify(medicineRepository, times(1)).delete(medicine);
    }

    @Test
    void shouldGetExpiredMedicinesSuccessfully() {

        when(medicineRepository.findByExpiryDateBefore(any(LocalDate.class))).thenReturn(List.of(medicine));
        List<MedicineResponseDTO> response = medicineService.getExpiredMedicines();
        assertFalse(response.isEmpty());
    }

    @Test
    void shouldGetLowStockMedicinesSuccessfully() {

        medicine.setQuantityInStock(5);
        medicine.setMinimumStockThreshold(10);
        when(medicineRepository.findAll()).thenReturn(List.of(medicine));
        List<MedicineResponseDTO> response = medicineService.getLowStockMedicines();
        assertFalse(response.isEmpty());
        assertEquals("Paracetamol", response.get(0).getName());
    }

    @Test
    void shouldGetExpiringSoonMedicinesSuccessfully() {

        when(medicineRepository.findByExpiryDateBetween(any(LocalDate.class),
                        any(LocalDate.class))).thenReturn(List.of(medicine));
        List<MedicineResponseDTO> response = medicineService.getExpiringSoonMedicines(30);
        assertFalse(response.isEmpty());
    }

    @Test
    void shouldGetPrescriptionRequiredMedicinesSuccessfully() {

        when(medicineRepository.findByPrescriptionRequiredTrue()).thenReturn(List.of(medicine));
        List<MedicineResponseDTO> response = medicineService.getPrescriptionRequiredMedicines();
        assertFalse(response.isEmpty());
    }

    @Test
    void shouldDeactivateMedicineSuccessfully() {

        when(medicineRepository.findById(1L)).thenReturn(Optional.of(medicine));
        when(medicineRepository.save(any(Medicine.class))).thenReturn(medicine);
        MedicineResponseDTO response = medicineService.deactivateMedicine(1L);
        assertNotNull(response);
        verify(medicineRepository, times(1)).save(any(Medicine.class));
    }

    @Test
    void shouldUpdateMedicinePriceSuccessfully() {

        when(medicineRepository.findById(1L)).thenReturn(Optional.of(medicine));
        when(medicineRepository.save(any(Medicine.class))).thenReturn(medicine);
        MedicineResponseDTO response = medicineService.updateMedicinePrice(1L, 99.0);
        assertNotNull(response);
        verify(medicineRepository, times(1)).save(any(Medicine.class));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingMissingMedicine() {

        MedicineRequestDTO dto = new MedicineRequestDTO();
        when(medicineRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(MedicineNotFoundException.class,() -> medicineService.updateMedicine(1L, dto));
    }

    @Test
    void shouldGetMedicinesByCategorySuccessfully() {

        when(medicineRepository.findByCategory(any())).thenReturn(List.of(medicine));
        List<MedicineResponseDTO> response = medicineService.getMedicinesByCategory("TABLET");
        assertFalse(response.isEmpty());
    }

    @Test
    void shouldGetMedicinesBySupplierSuccessfully() {

        when(medicineRepository.findBySupplierId(1L)).thenReturn(List.of(medicine));
        List<MedicineResponseDTO> response = medicineService.getMedicinesBySupplier(1L);
        assertFalse(response.isEmpty());
    }

    @Test
    void shouldSearchMedicinesSuccessfully() {

        when(medicineRepository.findByNameContainingIgnoreCaseOrGenericNameContainingIgnoreCase("Para", "Para")).thenReturn(List.of(medicine));
        List<MedicineResponseDTO> response = medicineService.searchMedicines("Para");
        assertFalse(response.isEmpty());
    }

    @Test
    void shouldDeleteExpiredMedicinesSuccessfully() {

        when(medicineRepository.findByExpiryDateBefore(any(LocalDate.class))).thenReturn(List.of(medicine));
        doNothing().when(medicineRepository).deleteAll(anyList());
        medicineService.deleteExpiredMedicines();
        verify(medicineRepository, times(1)).deleteAll(anyList());
    }

    @Test
    void shouldThrowExceptionForInvalidRestockQuantity() {

        InvalidQuantityException exception =
                assertThrows(InvalidQuantityException.class, () -> medicineService.restockMedicine(1L, -10));
        assertTrue(exception.getMessage().contains("greater than 0"));
    }

    @Test
    void shouldThrowExceptionForInvalidDispenseQuantity() {

        InvalidQuantityException exception = assertThrows(InvalidQuantityException.class, () -> medicineService.dispenseMedicine(1L, -5));
        assertTrue(exception.getMessage().contains("greater than 0"));
    }
    @Test
    void shouldThrowExceptionWhenDeletingMissingMedicine() {

        when(medicineRepository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> medicineService.deleteMedicine(1L));
        assertTrue(exception.getMessage().contains("Medicine not found"));
    }
    @Test
    void shouldThrowExceptionWhenRestockingMissingMedicine() {

        when(medicineRepository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> medicineService.restockMedicine(1L, 10));
        assertTrue(exception.getMessage().contains("Medicine not found"));
    }

    @Test
    void shouldThrowExceptionWhenDispensingMissingMedicine() {

        when(medicineRepository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> medicineService.dispenseMedicine(1L, 5));
        assertTrue(exception.getMessage().contains("Medicine not found"));
    }

}
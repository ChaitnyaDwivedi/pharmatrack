package com.pharmatrack.pharmatrack.service;

import com.pharmatrack.pharmatrack.dto.SupplierRequestDTO;
import com.pharmatrack.pharmatrack.dto.SupplierResponseDTO;
import com.pharmatrack.pharmatrack.model.Supplier;
import com.pharmatrack.pharmatrack.repository.SupplierRepository;
import com.pharmatrack.pharmatrack.service.impl.SupplierServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SupplierServiceImplTest {
    @Mock
    private SupplierRepository supplierRepository;
    @InjectMocks
    private SupplierServiceImpl supplierService;
    private Supplier supplier;
    private SupplierRequestDTO requestDTO;
    @BeforeEach
    void setUp(){

        supplier = new Supplier();

        supplier.setId(1L);

        supplier.setSupplierName("ABC Suppliers");

        supplier.setContactPerson("John");

        supplier.setEmail("abc@gmail.com");

        supplier.setPhoneNumber("9876543210");

        supplier.setAddress("Delhi");

        supplier.setIsActive(true);

        requestDTO = new SupplierRequestDTO();

        requestDTO.setSupplierName("ABC Suppliers");

        requestDTO.setContactPerson("John");

        requestDTO.setEmail("abc@gmail.com");
        requestDTO.setPhoneNumber("9876543210");
        requestDTO.setAddress("Delhi");
    }

    @Test
    void shouldAddSupplierSuccessfully() {

        when(supplierRepository.save(any(Supplier.class))).thenReturn(supplier);
        SupplierResponseDTO response = supplierService.addSupplier(requestDTO);
        assertNotNull(response);

        assertEquals("ABC Suppliers", response.getSupplierName());
        verify(supplierRepository, times(1)).save(any(Supplier.class));
    }

    @Test
    void shouldGetSupplierByIdSuccessfully() {

        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));
        SupplierResponseDTO response = supplierService.getSupplierById(1L);
        assertNotNull(response);
        assertEquals("ABC Suppliers", response.getSupplierName());

        verify(supplierRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenSupplierNotFound() {

        when(supplierRepository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> supplierService.getSupplierById(1L));
        assertTrue(exception.getMessage().contains("Supplier not found"));
    }

    @Test
    void shouldGetAllSuppliersSuccessfully() {

        when(supplierRepository.findAll(any(PageRequest.class))).thenReturn(
                new PageImpl<>(List.of(supplier)));

        List<SupplierResponseDTO> response = supplierService.getAllSuppliers(0, 5);
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
        verify(supplierRepository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void shouldSearchSupplierSuccessfully() {
        when(supplierRepository.findBySupplierNameContainingIgnoreCase("ABC")
        ).thenReturn(List.of(supplier));
        List<SupplierResponseDTO> response = supplierService.searchSupplier("ABC");
        assertFalse(response.isEmpty());
        assertEquals("ABC Suppliers", response.get(0).getSupplierName());
    }

    @Test
    void shouldActivateSupplierSuccessfully() {

        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));
        when(supplierRepository.save(any(Supplier.class))).thenReturn(supplier);
        SupplierResponseDTO response = supplierService.activateSupplier(1L);
        assertNotNull(response);
        verify(supplierRepository, times(1)).save(any(Supplier.class));
    }

    @Test
    void shouldDeactivateSupplierSuccessfully() {

        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));
        when(supplierRepository.save(any(Supplier.class))).thenReturn(supplier);
        SupplierResponseDTO response = supplierService.deactivateSupplier(1L);
        assertNotNull(response);
        verify(supplierRepository, times(1)).save(any(Supplier.class));
    }

    @Test
    void shouldDeleteSupplierSuccessfully() {

        doNothing()
                .when(supplierRepository)
                .deleteById(1L);
        supplierService.deleteSupplier(1L);
        verify(supplierRepository, times(1)).deleteById(1L);
    }
}
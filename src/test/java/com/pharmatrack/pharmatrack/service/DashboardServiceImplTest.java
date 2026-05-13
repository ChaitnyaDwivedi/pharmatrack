package com.pharmatrack.pharmatrack.service;

import com.pharmatrack.pharmatrack.dto.DashboardSummaryDTO;
import com.pharmatrack.pharmatrack.dto.MedicineResponseDTO;
import com.pharmatrack.pharmatrack.model.Category;
import com.pharmatrack.pharmatrack.model.Medicine;
import com.pharmatrack.pharmatrack.repository.MedicineRepository;
import com.pharmatrack.pharmatrack.repository.SupplierRepository;
import com.pharmatrack.pharmatrack.service.impl.DashboardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DashboardServiceImplTest {

    @Mock
    private MedicineRepository medicineRepository;

    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private DashboardServiceImpl dashboardService;

    private Medicine medicine;

    @BeforeEach
    void setUp() {
        medicine = new Medicine();
        medicine.setId(1L);
        medicine.setName("Paracetamol");
        medicine.setCategory(Category.TABLET);
        medicine.setQuantityInStock(100);
        medicine.setMinimumStockThreshold(20);
        medicine.setExpiryDate(LocalDate.now().plusDays(10));
    }

    @Test
    void shouldGetDashboardSummarySuccessfully() {
        when(medicineRepository.count()).thenReturn(10L);
        when(supplierRepository.count()).thenReturn(5L);
        when(medicineRepository.findByQuantityInStockLessThanEqual(10)).thenReturn(List.of(medicine));

        when(medicineRepository.findByExpiryDateBefore(LocalDate.now())).thenReturn(List.of());
        DashboardSummaryDTO response = dashboardService.getSummary();
        assertNotNull(response);
        assertEquals(10L, response.getTotalMedicines());

        assertEquals(5L, response.getTotalSuppliers());
    }

    @Test
    void shouldGetStockOverviewSuccessfully() {

        when(medicineRepository.findAll()).thenReturn(List.of(medicine));
        Map<String, Integer> response = dashboardService.getStockOverview();
        assertNotNull(response);
        assertTrue(response.containsKey("TABLET"));
    }

    @Test
    void shouldGetExpiryAlertsSuccessfully() {

        when(medicineRepository.findByExpiryDateBetween(LocalDate.now(), LocalDate.now().plusDays(30))).thenReturn(List.of(medicine));

        List<MedicineResponseDTO> response = dashboardService.getExpiryAlerts();
        assertFalse(response.isEmpty());
        assertEquals("Paracetamol", response.get(0).getName()
        );
    }
}
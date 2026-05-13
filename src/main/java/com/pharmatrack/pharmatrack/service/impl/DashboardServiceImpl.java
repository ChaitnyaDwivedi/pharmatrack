package com.pharmatrack.pharmatrack.service.impl;

import java.util.Map;
import java.util.List;
import java.util.HashMap;

import com.pharmatrack.pharmatrack.model.Medicine;
import com.pharmatrack.pharmatrack.dto.DashboardSummaryDTO;
import com.pharmatrack.pharmatrack.repository.MedicineRepository;
import com.pharmatrack.pharmatrack.repository.SupplierRepository;
import com.pharmatrack.pharmatrack.service.DashboardService;
import com.pharmatrack.pharmatrack.dto.MedicineResponseDTO;
import com.pharmatrack.pharmatrack.util.MedicineMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    private final MedicineRepository medicineRepository;

    private final SupplierRepository supplierRepository;

    @Override
    public DashboardSummaryDTO getSummary() {

        long totalMedicines = medicineRepository.count();

        long totalSuppliers = supplierRepository.count();

        long lowStockCount = medicineRepository.findByQuantityInStockLessThanEqual(10).size();

        long expiredCount = medicineRepository.findByExpiryDateBefore(LocalDate.now()).size();

        return DashboardSummaryDTO.builder().totalMedicines(totalMedicines).totalSuppliers(totalSuppliers).lowStockCount(lowStockCount).expiredCount(expiredCount).build();
    }

    @Override
    public Map<String, Integer> getStockOverview() {

        Map<String, Integer> stockOverview = new HashMap<>();

        List<Medicine> medicines = medicineRepository.findAll();

        for (Medicine medicine : medicines) {

            String category = medicine.getCategory().name();

            Integer currentStock = medicine.getQuantityInStock();

            stockOverview.put(category, stockOverview.getOrDefault(category, 0) + currentStock);
        }

        return stockOverview;
    }

    @Override
    public List<MedicineResponseDTO> getExpiryAlerts() {

        return medicineRepository.findByExpiryDateBetween(LocalDate.now(), LocalDate.now().plusDays(30)).stream().map(MedicineMapper::toDTO).toList();
    }
}

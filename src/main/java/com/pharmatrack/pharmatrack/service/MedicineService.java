package com.pharmatrack.pharmatrack.service;

import com.pharmatrack.pharmatrack.dto.MedicineRequestDTO;
import com.pharmatrack.pharmatrack.dto.MedicineResponseDTO;

import java.util.List;

public interface MedicineService {

    MedicineResponseDTO addMedicine(MedicineRequestDTO dto);
    void deleteMedicine(Long id);
    void deleteExpiredMedicines();

    List<MedicineResponseDTO> getAllMedicines(int page, int size, String sortBy);

    MedicineResponseDTO getMedicineById(Long id);

    List<MedicineResponseDTO> searchMedicines(String name);

    MedicineResponseDTO restockMedicine(Long id, Integer quantity);

    MedicineResponseDTO dispenseMedicine(Long id, Integer quantity);

    MedicineResponseDTO updateMedicine(Long id,MedicineRequestDTO dto);

    List<MedicineResponseDTO> getMedicinesByCategory(String category);

    List<MedicineResponseDTO> getMedicinesBySupplier(Long supplierId);

    List<MedicineResponseDTO> getExpiredMedicines();

    List<MedicineResponseDTO> getLowStockMedicines();

    List<MedicineResponseDTO> getExpiringSoonMedicines(int days);

    List<MedicineResponseDTO> getPrescriptionRequiredMedicines();

    MedicineResponseDTO updateMedicinePrice(Long id,Double newPrice);

    MedicineResponseDTO deactivateMedicine(Long id);
}
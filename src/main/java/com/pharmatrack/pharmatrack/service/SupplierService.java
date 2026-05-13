package com.pharmatrack.pharmatrack.service;

import com.pharmatrack.pharmatrack.dto.*;
import com.pharmatrack.pharmatrack.dto.MedicineResponseDTO;
import java.util.List;

public interface SupplierService {

    SupplierResponseDTO addSupplier(SupplierRequestDTO dto);

    List<SupplierResponseDTO> getAllSuppliers(int page, int size);

    SupplierResponseDTO getSupplierById(Long id);

    List<SupplierResponseDTO> searchSupplier(String name);

    SupplierResponseDTO activateSupplier(Long id);

    SupplierResponseDTO deactivateSupplier(Long id);

    void deleteSupplier(Long id);

    SupplierResponseDTO updateSupplier(Long id, SupplierRequestDTO dto);

    List<MedicineResponseDTO> getMedicinesBySupplier(Long supplierId);

}
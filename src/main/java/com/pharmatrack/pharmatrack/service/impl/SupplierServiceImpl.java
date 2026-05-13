package com.pharmatrack.pharmatrack.service.impl;

import com.pharmatrack.pharmatrack.dto.MedicineResponseDTO;
import com.pharmatrack.pharmatrack.dto.SupplierRequestDTO;
import com.pharmatrack.pharmatrack.dto.SupplierResponseDTO;
import com.pharmatrack.pharmatrack.model.Supplier;
import com.pharmatrack.pharmatrack.repository.MedicineRepository;
import com.pharmatrack.pharmatrack.repository.SupplierRepository;
import com.pharmatrack.pharmatrack.service.SupplierService;
import com.pharmatrack.pharmatrack.util.MedicineMapper;
import com.pharmatrack.pharmatrack.util.SupplierMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    private final MedicineRepository medicineRepository;

    @Override
    public SupplierResponseDTO addSupplier(SupplierRequestDTO dto) {

        Supplier supplier = new Supplier();

        supplier.setSupplierName(dto.getSupplierName());
        supplier.setContactPerson(dto.getContactPerson());
        supplier.setEmail(dto.getEmail());
        supplier.setPhoneNumber(dto.getPhoneNumber());
        supplier.setAddress(dto.getAddress());
        supplier.setIsActive(true);

        Supplier saved = supplierRepository.save(supplier);

        return SupplierMapper.toDTO(saved);
    }

    @Override
    public List<SupplierResponseDTO> getAllSuppliers(int page, int size) {

        return supplierRepository.findAll(PageRequest.of(page, size)).getContent().stream().map(SupplierMapper::toDTO).toList();
    }

    @Override
    public SupplierResponseDTO getSupplierById(Long id) {

        Supplier supplier = supplierRepository.findById(id).orElseThrow(() -> new RuntimeException("Supplier not found"));

        return SupplierMapper.toDTO(supplier);
    }

    @Override
    public List<SupplierResponseDTO> searchSupplier(String name) {

        return supplierRepository.findBySupplierNameContainingIgnoreCase(name).stream().map(SupplierMapper::toDTO).toList();
    }

    @Override
    public SupplierResponseDTO activateSupplier(Long id) {

        Supplier supplier = supplierRepository.findById(id).orElseThrow(() -> new RuntimeException("Supplier not found"));

        supplier.setIsActive(true);

        Supplier updated = supplierRepository.save(supplier);

        return SupplierMapper.toDTO(updated);
    }

    @Override
    public SupplierResponseDTO deactivateSupplier(Long id) {

        Supplier supplier = supplierRepository.findById(id).orElseThrow(() -> new RuntimeException("Supplier not found"));

        supplier.setIsActive(false);

        Supplier updated = supplierRepository.save(supplier);

        return SupplierMapper.toDTO(updated);
    }

    @Override
    public void deleteSupplier(Long id) {

        supplierRepository.deleteById(id);
    }

    @Override
    public SupplierResponseDTO updateSupplier(Long id, SupplierRequestDTO dto) {

        Supplier supplier = supplierRepository.findById(id).orElseThrow(() -> new RuntimeException("Supplier not found with id: " + id));

        supplier.setSupplierName(dto.getSupplierName());
        supplier.setContactPerson(dto.getContactPerson());
        supplier.setEmail(dto.getEmail());
        supplier.setPhoneNumber(dto.getPhoneNumber());
        supplier.setAddress(dto.getAddress());

        Supplier updated = supplierRepository.save(supplier);

        return SupplierMapper.toDTO(updated);
    }

    @Override
    public List<MedicineResponseDTO> getMedicinesBySupplier(Long supplierId) {

        return medicineRepository.findBySupplierId(supplierId).stream().map(MedicineMapper::toDTO).toList();
    }
}
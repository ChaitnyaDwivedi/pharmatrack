package com.pharmatrack.pharmatrack.service.impl;

import com.pharmatrack.pharmatrack.dto.MedicineRequestDTO;
import com.pharmatrack.pharmatrack.dto.MedicineResponseDTO;
import com.pharmatrack.pharmatrack.exception.*;
import com.pharmatrack.pharmatrack.model.Category;
import com.pharmatrack.pharmatrack.model.Medicine;
import com.pharmatrack.pharmatrack.model.StockTransaction;
import com.pharmatrack.pharmatrack.model.Supplier;
import com.pharmatrack.pharmatrack.model.TransactionType;
import com.pharmatrack.pharmatrack.repository.MedicineRepository;
import com.pharmatrack.pharmatrack.repository.StockTransactionRepository;
import com.pharmatrack.pharmatrack.repository.SupplierRepository;
import com.pharmatrack.pharmatrack.service.MedicineService;
import com.pharmatrack.pharmatrack.util.MedicineMapper;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository medicineRepository;

    private final SupplierRepository supplierRepository;

    private final StockTransactionRepository stockTransactionRepository;

    @Override
    public MedicineResponseDTO addMedicine(MedicineRequestDTO dto) {

        Medicine medicine = new Medicine();

        medicine.setName(dto.getName());
        medicine.setGenericName(dto.getGenericName());
        medicine.setManufacturer(dto.getManufacturer());
        medicine.setCategory(dto.getCategory());
        medicine.setPrice(dto.getPrice());
        medicine.setQuantityInStock(dto.getQuantityInStock());
        medicine.setMinimumStockThreshold(dto.getMinimumStockThreshold());
        medicine.setExpiryDate(dto.getExpiryDate());
        medicine.setManufacturedDate(dto.getManufacturedDate());
        medicine.setBatchNumber(dto.getBatchNumber());
        medicine.setPrescriptionRequired(dto.getPrescriptionRequired());
        medicine.setIsActive(true);

        // Supplier Mapping
        if (dto.getSupplierId() != null) {

            Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                    .orElseThrow(() ->
                            new RuntimeException("Supplier not found"));

            medicine.setSupplier(supplier);
        }

        Medicine saved = medicineRepository.save(medicine);

        return MedicineMapper.toDTO(saved);
    }

    @Override
    public List<MedicineResponseDTO> getAllMedicines(int page, int size, String sortBy) {

        Page<Medicine> medicines =
                medicineRepository.findAll(
                        PageRequest.of(
                                page,
                                size,
                                org.springframework.data.domain.Sort.by(sortBy)));
        return medicines.getContent()
                .stream()
                .map(MedicineMapper::toDTO)
                .toList();
    }

    @Override
    public MedicineResponseDTO getMedicineById(Long id) {

        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new MedicineNotFoundException("Medicine not found with id: " + id));
        return MedicineMapper.toDTO(medicine);
    }

    @Override
    public List<MedicineResponseDTO> searchMedicines(String name) {

        return medicineRepository
                .findByNameContainingIgnoreCaseOrGenericNameContainingIgnoreCase(name, name)
                .stream()
                .map(MedicineMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MedicineResponseDTO restockMedicine(Long id, Integer quantity) {

        if (quantity <= 0) {
            throw new InvalidQuantityException("Quantity must be greater than 0");
        }

        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new MedicineNotFoundException("Medicine not found with id: " + id));
        medicine.setQuantityInStock(
                medicine.getQuantityInStock() + quantity
        );

        Medicine updated = medicineRepository.save(medicine);

        // Transaction Logging
        StockTransaction transaction = StockTransaction.builder()
                .medicine(updated)
                .transactionType(TransactionType.RESTOCK)
                .quantity(quantity)
                .remarks("Medicine restocked")
                .build();

        stockTransactionRepository.save(transaction);
        return MedicineMapper.toDTO(updated);
    }

    @Override
    public MedicineResponseDTO dispenseMedicine(Long id, Integer quantity) {

        if (quantity <= 0) {
            throw new InvalidQuantityException("Quantity must be greater than 0");
        }

        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() ->
                        new MedicineNotFoundException("Medicine not found with id: " + id));

        if (medicine.getExpiryDate().isBefore(LocalDate.now())) {
            throw new MedicineExpiredException("Medicine is expired");
        }

        if (medicine.getQuantityInStock() < quantity) {

            throw new OutOfStockException("Insufficient stock. Requested: " + quantity + ", Available: " + medicine.getQuantityInStock());
        }
        medicine.setQuantityInStock(medicine.getQuantityInStock() - quantity);

        Medicine updated = medicineRepository.save(medicine);

        // Transaction Logging
        StockTransaction transaction = StockTransaction.builder()
                .medicine(updated)
                .transactionType(TransactionType.DISPENSED)
                .quantity(quantity)
                .remarks("Medicine dispensed")
                .build();
        stockTransactionRepository.save(transaction);
        return MedicineMapper.toDTO(updated);
    }

    @Override
    public void deleteMedicine(Long id) {

        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new MedicineNotFoundException("Medicine not found"));
        medicineRepository.delete(medicine);
    }

    @Override
    public MedicineResponseDTO updateMedicine(Long id, MedicineRequestDTO dto) {

        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new MedicineNotFoundException("Medicine not found with id: " + id));

        medicine.setName(dto.getName());
        medicine.setGenericName(dto.getGenericName());
        medicine.setManufacturer(dto.getManufacturer());
        medicine.setCategory(dto.getCategory());
        medicine.setPrice(dto.getPrice());
        medicine.setQuantityInStock(dto.getQuantityInStock());
        medicine.setMinimumStockThreshold(dto.getMinimumStockThreshold());
        medicine.setExpiryDate(dto.getExpiryDate());
        medicine.setManufacturedDate(dto.getManufacturedDate());
        medicine.setBatchNumber(dto.getBatchNumber());
        medicine.setPrescriptionRequired(dto.getPrescriptionRequired());

        // Supplier Mapping
        if (dto.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                    .orElseThrow(() -> new RuntimeException("Supplier not found"));
            medicine.setSupplier(supplier);
        }
        Medicine updated = medicineRepository.save(medicine);
        return MedicineMapper.toDTO(updated);
    }

    @Override
    public List<MedicineResponseDTO> getMedicinesByCategory(String category) {

        return medicineRepository.findByCategory(
                        Category.valueOf(category.toUpperCase())
                )
                .stream()
                .map(MedicineMapper::toDTO)
                .toList();
    }

    @Override
    public List<MedicineResponseDTO> getMedicinesBySupplier(Long supplierId) {

        return medicineRepository.findBySupplierId(supplierId)
                .stream()
                .map(MedicineMapper::toDTO)
                .toList();
    }

    @Override
    public List<MedicineResponseDTO> getExpiredMedicines() {

        return medicineRepository.findByExpiryDateBefore(LocalDate.now())
                .stream()
                .map(MedicineMapper::toDTO)
                .toList();
    }
    @Override
    public List<MedicineResponseDTO> getLowStockMedicines() {

        return medicineRepository.findAll()
                .stream()
                .filter(m ->
                        m.getQuantityInStock() <
                                m.getMinimumStockThreshold()
                )
                .map(MedicineMapper::toDTO)
                .toList();
    }

    @Override
    public List<MedicineResponseDTO> getExpiringSoonMedicines(int days) {

        LocalDate today = LocalDate.now();

        LocalDate futureDate = today.plusDays(days);

        return medicineRepository
                .findByExpiryDateBetween(today, futureDate)
                .stream()
                .map(MedicineMapper::toDTO)
                .toList();
    }

    @Override
    public List<MedicineResponseDTO> getPrescriptionRequiredMedicines() {

        return medicineRepository
                .findByPrescriptionRequiredTrue()
                .stream()
                .map(MedicineMapper::toDTO)
                .toList();
    }

    @Override
    public MedicineResponseDTO updateMedicinePrice(
            Long id,
            Double newPrice
    ) {

        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new MedicineNotFoundException("Medicine not found with id: " + id));
        medicine.setPrice(newPrice);
        Medicine updated = medicineRepository.save(medicine);
        return MedicineMapper.toDTO(updated);
    }

    @Override
    public MedicineResponseDTO deactivateMedicine(Long id) {

        Medicine medicine = medicineRepository.findById(id).orElseThrow(() -> new MedicineNotFoundException("Medicine not found with id: " + id));
        medicine.setIsActive(false);
        Medicine updated = medicineRepository.save(medicine);
        return MedicineMapper.toDTO(updated);
    }

    @Override
    public void deleteExpiredMedicines() {
        List<Medicine> expiredMedicines = medicineRepository.findByExpiryDateBefore(LocalDate.now());
        medicineRepository.deleteAll(expiredMedicines);
    }
}
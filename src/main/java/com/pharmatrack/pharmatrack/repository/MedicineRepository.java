package com.pharmatrack.pharmatrack.repository;

import com.pharmatrack.pharmatrack.model.Medicine;
import com.pharmatrack.pharmatrack.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    List<Medicine> findByNameContainingIgnoreCaseOrGenericNameContainingIgnoreCase(String name, String genericName);

    List<Medicine> findByCategory(Category category);

    List<Medicine> findBySupplierId(Long supplierId);

    List<Medicine> findByExpiryDateBefore(LocalDate date);

    List<Medicine> findByExpiryDateBetween(LocalDate start, LocalDate end);

    List<Medicine> findByQuantityInStockLessThan(Integer quantity);
    List<Medicine> findByQuantityInStockLessThanEqual(Integer quantity);

    List<Medicine> findByPrescriptionRequiredTrue();

}
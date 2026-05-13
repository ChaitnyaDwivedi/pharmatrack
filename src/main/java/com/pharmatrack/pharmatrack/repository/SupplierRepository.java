package com.pharmatrack.pharmatrack.repository;

import com.pharmatrack.pharmatrack.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    List<Supplier> findBySupplierNameContainingIgnoreCase(String name);
}
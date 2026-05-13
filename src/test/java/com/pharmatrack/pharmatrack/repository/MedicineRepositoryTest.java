package com.pharmatrack.pharmatrack.repository;

import com.pharmatrack.pharmatrack.model.Medicine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
class MedicineRepositoryTest {

    @Autowired
    private MedicineRepository medicineRepository;

    @Test
    void shouldFindExpiredMedicines() {

        Medicine medicine = new Medicine();
        medicine.setName("Expired Medicine");
        medicine.setPrice(50.0);
        medicine.setQuantityInStock(10);
        medicine.setMinimumStockThreshold(2);
        medicine.setExpiryDate(LocalDate.now().minusDays(1));

        medicineRepository.save(medicine);

        List<Medicine> medicines = medicineRepository.findByExpiryDateBefore(LocalDate.now());
        assertFalse(medicines.isEmpty());
        assertEquals("Expired Medicine", medicines.get(0).getName());
    }
}
package com.pharmatrack.pharmatrack.dto;


import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SupplierRequestDTO {

    @NotBlank(message = "Supplier name required")
    private String supplierName;

    @NotBlank(message = "Contact person required")
    private String contactPerson;

    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Phone number required")
    private String phoneNumber;

    private String address;
}

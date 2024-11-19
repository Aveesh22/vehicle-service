package com.apollo.vehicle.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class SoldVehicle
{
    @Id
    @Column(unique = true, nullable = false)
    @NotNull(message = "VIN cannot be null")
    @Size(min = 17, max = 17, message = "VIN must be exactly 17 characters")
    private String vin;

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Sale price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Sale price must be greater than 0")
    private BigDecimal salePrice;

    @Column(nullable = false)
    @NotBlank(message = "Transaction type cannot be null")
    private String transactionType;
}

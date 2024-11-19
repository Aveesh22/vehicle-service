package com.apollo.vehicle.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    @Id
    @Column(unique = true, nullable = false)
    @NotNull(message = "VIN cannot be null")
    @Size(min = 17, max = 17, message = "VIN must be exactly 17 characters")
    private String vin;

    @Column(nullable = false)
    @NotBlank(message = "Manufacturer name cannot be null")
    private String manufacturerName;

    @Column(nullable = false)
    @NotBlank(message = "Description cannot be null")
    private String description;

    @Column(nullable = false)
    @NotNull(message = "Horse power cannot be null")
    @DecimalMin(value = "1", inclusive = true, message = "Horse power must be greater than 0")
    private int horsePower;

    @Column(nullable = false)
    @NotBlank(message = "Model name cannot be null")
    private String modelName;

    @Column(nullable = false)
    @NotNull(message = "Model year cannot be null")
    private int modelYear;

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Purchase price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Purchase price must be greater than 0")
    private BigDecimal purchasePrice;

    @Column(nullable = false)
    @NotBlank(message = "Fuel type cannot be null")
    private String fuelType;

    @Column(nullable = false)
    @NotBlank(message = "Color cannot be null")
    private String color;

    @Column(nullable = false)
    @NotBlank(message = "Category cannot be null")
    private String category;
}

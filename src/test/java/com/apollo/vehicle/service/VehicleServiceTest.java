package com.apollo.vehicle.service;

import com.apollo.vehicle.entity.Vehicle;
import com.apollo.vehicle.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class VehicleServiceTest {

    @InjectMocks
    private VehicleService vehicleService;

    @Mock
    private VehicleRepository vehicleRepository;

    private Vehicle vehicle;
    private Vehicle vehicle2;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        vehicle = new Vehicle(
                "ABCDE12345ABCDE12",
                "Toyota",
                "SUV",
                150,
                "Camry",
                2020,
                new BigDecimal("25000.00"),
                "Gasoline"
        );
        vehicle2 = new Vehicle(
                "ABCDE12345",
                "Toyota",
                "SUV",
                180,
                "Sedan",
                2024,
                new BigDecimal("29000.00"),
                "Gasoline"
        );
    }

    @Test
    void testGetVehicleByVin() {
        when(vehicleRepository.findById(vehicle.getVin())).thenReturn(Optional.of(vehicle));

        Vehicle foundVehicle = vehicleService.getVehicleByVin(vehicle.getVin());

        assertThat(foundVehicle).isNotNull();
        assertThat(foundVehicle.getVin()).isEqualTo(vehicle.getVin());
        assertThat(foundVehicle.getManufacturerName()).isEqualTo(vehicle.getManufacturerName());

        verify(vehicleRepository, times(1)).findById(vehicle.getVin());
    }

    @Test
    void testCreateVehicle() {
        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);

        Vehicle savedVehicle = vehicleService.createVehicle(vehicle);

        assertThat(savedVehicle).isNotNull();
        assertThat(savedVehicle.getVin()).isEqualTo(vehicle.getVin());

        verify(vehicleRepository, times(1)).save(vehicle);
    }

    @Test
    void testDeleteVehicle() {
        when(vehicleRepository.existsById(vehicle.getVin())).thenReturn(true);
        doNothing().when(vehicleRepository).deleteById(vehicle.getVin());

        vehicleService.deleteVehicle(vehicle.getVin());

        verify(vehicleRepository, times(1)).existsById(vehicle.getVin());
        verify(vehicleRepository, times(1)).deleteById(vehicle.getVin());
    }

    @Test
    void testNotCreateWithDuplicateVin() {
        when(vehicleRepository.existsById(vehicle.getVin())).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                vehicleService.createVehicle(vehicle)
        );

        assertThat(exception.getMessage()).isEqualTo("Vehicle with VIN " + vehicle.getVin() + " already exists.");

        verify(vehicleRepository, times(1)).existsById(vehicle.getVin());
        verify(vehicleRepository, never()).save(vehicle);
    }

    @Test
    void testThrowExceptionWhenDeletingNonExistentVehicle() {
        when(vehicleRepository.existsById(vehicle.getVin())).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                vehicleService.deleteVehicle(vehicle.getVin())
        );

        assertThat(exception.getMessage()).isEqualTo("Vehicle with VIN " + vehicle.getVin() + " not found.");

        verify(vehicleRepository, times(1)).existsById(vehicle.getVin());
        verify(vehicleRepository, never()).deleteById(vehicle.getVin());
    }

    @Test
    void testGetAllVehicles() {
        when(vehicleRepository.findAll()).thenReturn(Collections.singletonList(vehicle));

        List<Vehicle> vehicles = vehicleService.getAllVehicles();

        assertThat(vehicles).isNotNull();
        assertThat(vehicles).hasSize(1);
        assertThat(vehicles.get(0).getVin()).isEqualTo(vehicle.getVin());

        verify(vehicleRepository, times(1)).findAll();
    }
}

package com.apollo.vehicle.controller;

import com.apollo.vehicle.entity.Vehicle;
import com.apollo.vehicle.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/vehicle")
public class VehicleController {
    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    /**
     * Retrieve all vehicles
     * @return a ResponseEntity containing the list of all vehicles with a 200 OK status
     */
    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        return ResponseEntity.ok(vehicles);
    }

    /**
     * Retrieve a specific vehicle by VIN
     * @param vin the VIN of the vehicle to retrieve
     * @return a ResponseEntity containing the retrieved vehicle with a 200 OK status
     */
    @GetMapping("/{vin}")
    public ResponseEntity<Vehicle> getVehicle(@PathVariable String vin) {
        Vehicle vehicle = vehicleService.getVehicleByVin(vin);
        return ResponseEntity.ok(vehicle);
    }

    /**
     * Create a new vehicle
     * @param vehicle the vehicle to create
     * @return a ResponseEntity containing the created vehicle with a 201 Created status
     */
    @PostMapping
    public ResponseEntity<?> createVehicle(@Valid @RequestBody Vehicle vehicle) {
        Vehicle createdVehicle = vehicleService.createVehicle(vehicle);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVehicle); //Required 201 Created response
    }

    /**
     * Update an existing vehicle
     * @param vin the VIN of the vehicle to update
     * @param updates the updated vehicle data
     * @return a ResponseEntity containing the updated vehicle with a 200 OK status
     */
    @PutMapping("/{vin}")
    public ResponseEntity<?> updateVehicle(@PathVariable String vin, @Valid @RequestBody Vehicle updates) {
        Vehicle updatedVehicle = vehicleService.updateVehicle(vin, updates);
        return ResponseEntity.ok(updatedVehicle);
    }

    /**
     * Delete an existing vehicle
     * @param vin the VIN of the vehicle to delete
     * @return a ResponseEntity with a 204 No Content status
     */
    @DeleteMapping("/{vin}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable String vin) {
        vehicleService.deleteVehicle(vin);
        return ResponseEntity.noContent().build(); //Required 204 No Content response
    }
}

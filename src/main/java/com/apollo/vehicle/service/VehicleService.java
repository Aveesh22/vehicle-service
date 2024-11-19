package com.apollo.vehicle.service;

import com.apollo.vehicle.entity.Vehicle;
import com.apollo.vehicle.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    /**
     * Retrieves all vehicles from the database
     * @return a list of all vehicles
     */
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    /**
     * Retrieves a specific vehicles by its VIN from the database
     * @param vin the VIN of the vehicle to be retrieved
     * @return the vehicle, if found
     * @throws IllegalArgumentException if the vehicle is not found
     */
    public Vehicle getVehicleByVin(String vin) {
        return vehicleRepository.findById(vin)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle with VIN " + vin + " not found"));
    }

    /**
     * Creates a vehicle and saves it in the database
     * @param vehicle the vehicle to be created
     * @return the created vehicle
     * @throws IllegalArgumentException if a vehicle with the same VIN already exists in the database
     */
    public Vehicle createVehicle(Vehicle vehicle) {
        if (vehicleRepository.existsById(vehicle.getVin())) {
            throw new IllegalArgumentException("Vehicle with VIN " + vehicle.getVin() + " already exists.");
        }
        return vehicleRepository.save(vehicle);
    }

    /**
     * Updates an existing vehicle by its VIN and stores it in the database
     * @param vin the VIN of the vehicle to be updated
     * @param updatedVehicle the updated vehicle data
     * @return the updated vehicle
     */
    public Vehicle updateVehicle(String vin, Vehicle updatedVehicle) {
        Vehicle existingVehicle = getVehicleByVin(vin);

        Optional.ofNullable(updatedVehicle.getManufacturerName()).ifPresent(existingVehicle::setManufacturerName);
        Optional.ofNullable(updatedVehicle.getDescription()).ifPresent(existingVehicle::setDescription);

        if (updatedVehicle.getHorsePower() > 0) { // Check for valid primitive value
            existingVehicle.setHorsePower(updatedVehicle.getHorsePower());
        }

        Optional.ofNullable(updatedVehicle.getModelName()).ifPresent(existingVehicle::setModelName);

        if (updatedVehicle.getModelYear() > 0) { // Check for valid primitive value
            existingVehicle.setModelYear(updatedVehicle.getModelYear());
        }

        Optional.ofNullable(updatedVehicle.getPurchasePrice()).ifPresent(existingVehicle::setPurchasePrice);
        Optional.ofNullable(updatedVehicle.getFuelType()).ifPresent(existingVehicle::setFuelType);

        return vehicleRepository.save(existingVehicle);
    }

    /**
     * Deletes a vehicle in the database by its VIN
     * @param vin the VIN, if found, of the vehicle to be deleted
     * @throws IllegalArgumentException if the VIN does not correspond to a vehicle in the database
     */
    public void deleteVehicle(String vin) {
        if (!vehicleRepository.existsById(vin)) {
            throw new IllegalArgumentException("Vehicle with VIN " + vin + " not found.");
        }
        vehicleRepository.deleteById(vin);
    }
}


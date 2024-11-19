package com.apollo.vehicle.repository;

import com.apollo.vehicle.entity.SoldVehicle;
import com.apollo.vehicle.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoldVehicleRepository extends JpaRepository<SoldVehicle, String> {
}

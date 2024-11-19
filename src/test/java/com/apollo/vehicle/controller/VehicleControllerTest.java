package com.apollo.vehicle.controller;

import com.apollo.vehicle.controller.VehicleController;
import com.apollo.vehicle.entity.Vehicle;
import com.apollo.vehicle.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.Collections;


@WebMvcTest(controllers = VehicleController.class)
@AutoConfigureMockMvc
public class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleService vehicleService;

    private Vehicle vehicle;
    private Vehicle vehicle2;

    @BeforeEach
    void setUp() {
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
                "ABCDE12345ABCDE12",
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
    void testGetAllVehicles() throws Exception {
        when(vehicleService.getAllVehicles()).thenReturn(Collections.singletonList(vehicle));

        mockMvc.perform(get("/vehicle"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].vin").value(vehicle.getVin()))
                .andExpect(jsonPath("$[0].manufacturerName").value(vehicle.getManufacturerName()))
                .andExpect(jsonPath("$[0].description").value(vehicle.getDescription()))
                .andExpect(jsonPath("$[0].horsePower").value(vehicle.getHorsePower()))
                .andExpect(jsonPath("$[0].modelName").value(vehicle.getModelName()))
                .andExpect(jsonPath("$[0].modelYear").value(vehicle.getModelYear()))
                .andExpect(jsonPath("$[0].purchasePrice").value(vehicle.getPurchasePrice().doubleValue()))
                .andExpect(jsonPath("$[0].fuelType").value(vehicle.getFuelType()));

        verify(vehicleService, times(1)).getAllVehicles(); //ensure method is called once
    }

    @Test
    void testGetVehicleByVin() throws Exception {
        when(vehicleService.getVehicleByVin(vehicle.getVin())).thenReturn(vehicle);

        mockMvc.perform(get("/vehicle/{vin}", vehicle.getVin()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vin").value(vehicle.getVin()))
                .andExpect(jsonPath("$.manufacturerName").value(vehicle.getManufacturerName()))
                .andExpect(jsonPath("$.description").value(vehicle.getDescription()))
                .andExpect(jsonPath("$.horsePower").value(vehicle.getHorsePower()))
                .andExpect(jsonPath("$.modelName").value(vehicle.getModelName()))
                .andExpect(jsonPath("$.modelYear").value(vehicle.getModelYear()))
                .andExpect(jsonPath("$.purchasePrice").value(vehicle.getPurchasePrice().doubleValue()))
                .andExpect(jsonPath("$.fuelType").value(vehicle.getFuelType()));

        verify(vehicleService, times(1)).getVehicleByVin(vehicle.getVin());
    }

    @Test
    void testReturnNotFoundWhenVehicleNotExists() throws Exception {
        when(vehicleService.getVehicleByVin(vehicle.getVin()))
                .thenThrow(new IllegalArgumentException("Vehicle not found"));

        mockMvc.perform(get("/vehicle/{vin}", vehicle.getVin()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Vehicle not found"));

        verify(vehicleService, times(1)).getVehicleByVin(vehicle.getVin());
    }

    @Test
    void testCreateVehicle() throws Exception {
        when(vehicleService.createVehicle(Mockito.any(Vehicle.class))).thenReturn(vehicle);

        mockMvc.perform(post("/vehicle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "vin": "ABCDE12345ABCDE12",
                                    "manufacturerName": "Toyota",
                                    "description": "SUV",
                                    "horsePower": 150,
                                    "modelName": "Camry",
                                    "modelYear": 2020,
                                    "purchasePrice": 25000.00,
                                    "fuelType": "Gasoline"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.vin").value(vehicle.getVin()))
                .andExpect(jsonPath("$.manufacturerName").value(vehicle.getManufacturerName()));

        verify(vehicleService, times(1)).createVehicle(Mockito.any(Vehicle.class));
    }

    @Test
    void testUpdateVehicle() throws Exception {
        when(vehicleService.updateVehicle(eq(vehicle.getVin()), any(Vehicle.class))).thenReturn(vehicle2);

        // Perform PUT request
        mockMvc.perform(put("/vehicle/{vin}", vehicle.getVin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "vin": "ABCDE12345ABCDE12",
                                    "manufacturerName": "Toyota",
                                    "description": "SUV",
                                    "horsePower": 180,
                                    "modelName": "Sedan",
                                    "modelYear": 2024,
                                    "purchasePrice": 29000.00,
                                    "fuelType": "Gasoline"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vin").value(vehicle2.getVin()))
                .andExpect(jsonPath("$.horsePower").value(vehicle2.getHorsePower()))
                .andExpect(jsonPath("$.modelName").value(vehicle2.getModelName()))
                .andExpect(jsonPath("$.purchasePrice").value(vehicle2.getPurchasePrice().doubleValue()));

        verify(vehicleService, times(1)).updateVehicle(eq(vehicle.getVin()), any(Vehicle.class));
    }

    @Test
    void testDeleteVehicle() throws Exception {
        doNothing().when(vehicleService).deleteVehicle(vehicle.getVin());

        mockMvc.perform(delete("/vehicle/{vin}", vehicle.getVin()))
                .andExpect(status().isNoContent());

        verify(vehicleService, times(1)).deleteVehicle(vehicle.getVin());
    }

    @Test
    void testCreateVehicleValidationError() throws Exception {
        mockMvc.perform(post("/vehicle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "vin": "ABCDE12345ABCDE12",
                                    "manufacturerName": "Toyota",
                                    "horsePower": 150
                                }
                                """))
                .andExpect(status().isUnprocessableEntity()); // Expect 422 for validation errors

        verify(vehicleService, never()).createVehicle(any(Vehicle.class));
    }

    @Test
    void testCreateVehicleDuplicateVin() throws Exception {
        when(vehicleService.createVehicle(Mockito.any(Vehicle.class)))
                .thenThrow(new IllegalArgumentException("Vehicle with VIN already exists"));

        mockMvc.perform(post("/vehicle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "vin": "ABCDE12345ABCDE12",
                        "manufacturerName": "Toyota",
                        "description": "SUV",
                        "horsePower": 150,
                        "modelName": "Camry",
                        "modelYear": 2020,
                        "purchasePrice": 25000.00,
                        "fuelType": "Gasoline"
                    }
                    """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Vehicle with VIN already exists"));

        verify(vehicleService, times(1)).createVehicle(Mockito.any(Vehicle.class));
    }

    @Test
    void testGetAllVehiclesEmpty() throws Exception {
        when(vehicleService.getAllVehicles()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/vehicle"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());

        verify(vehicleService, times(1)).getAllVehicles();
    }
}
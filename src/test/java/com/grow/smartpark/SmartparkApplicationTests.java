package com.grow.smartpark;

import com.grow.smartpark.constants.SmartParkConstants;
import com.grow.smartpark.domain.ApiResponse;
import com.grow.smartpark.domain.VehicleRequest;
import com.grow.smartpark.domain.VehicleResponse;
import com.grow.smartpark.model.Vehicle;
import com.grow.smartpark.repository.VehicleRepository;
import com.grow.smartpark.service.impl.VehicleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceImplSimpleTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    private VehicleRequest request;
    private Vehicle vehicle;

    @BeforeEach
    void init() {
        request = new VehicleRequest();
        request.setLicensePlate("ABC-123");
        request.setOwnerName("John Doe");

        vehicle = new Vehicle();
        vehicle.setLicensePlate("ABC-123");
        vehicle.setOwnerName("John Doe");
    }

    @Test
    void registerVehicle_success() {
        when(vehicleRepository.findByLicensePlate("ABC-123")).thenReturn(Optional.empty());
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        ApiResponse<VehicleResponse> response = vehicleService.registerVehicle(request);

        assertEquals(SmartParkConstants.STATUS_SUCCESS, response.getStatus());
        assertEquals(SmartParkConstants.CODE_SUCCESS, response.getCode());
    }

    @Test
    void registerVehicle_duplicate() {
        when(vehicleRepository.findByLicensePlate("ABC-123")).thenReturn(Optional.of(vehicle));

        ApiResponse<VehicleResponse> response = vehicleService.registerVehicle(request);

        assertEquals(SmartParkConstants.STATUS_ERROR, response.getStatus());
        assertEquals(SmartParkConstants.CODE_VEHICLE_DUPLICATE, response.getCode());
    }

    @Test
    void registerVehicle_systemError() {
        when(vehicleRepository.findByLicensePlate("ABC-123")).thenReturn(Optional.empty());
        when(vehicleRepository.save(any(Vehicle.class))).thenThrow(new RuntimeException("DB error"));

        ApiResponse<VehicleResponse> response = vehicleService.registerVehicle(request);

        assertEquals(SmartParkConstants.STATUS_ERROR, response.getStatus());
        assertEquals(SmartParkConstants.CODE_SYSTEM_ERROR, response.getCode());
    }
}

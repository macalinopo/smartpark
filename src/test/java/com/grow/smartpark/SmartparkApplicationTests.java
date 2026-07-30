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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class SmartparkApplicationTests {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    private VehicleRequest request;
    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new VehicleRequest();
        request.setLicensePlate("ABC-123");
        request.setOwnerName("John Doe");

        vehicle = new Vehicle();
        vehicle.setLicensePlate("ABC-123");
        vehicle.setOwnerName("John Doe");
    }

    @Test
    void contextLoads() {
        // Basic Spring context test
    }

    @Test
    void testRegisterVehicle_Success() {
        when(vehicleRepository.findByLicensePlate("ABC-123")).thenReturn(Optional.empty());
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        ApiResponse<VehicleResponse> response = vehicleService.registerVehicle(request);

        assertEquals(SmartParkConstants.STATUS_SUCCESS, response.getStatus());
        assertEquals(SmartParkConstants.CODE_SUCCESS, response.getCode());
    }

    @Test
    void testRegisterVehicle_Duplicate() {
        when(vehicleRepository.findByLicensePlate("ABC-123")).thenReturn(Optional.of(vehicle));

        ApiResponse<VehicleResponse> response = vehicleService.registerVehicle(request);

        assertEquals(SmartParkConstants.STATUS_ERROR, response.getStatus());
        assertEquals(SmartParkConstants.CODE_VEHICLE_DUPLICATE, response.getCode());
    }

    @Test
    void testRegisterVehicle_Exception() {
        when(vehicleRepository.findByLicensePlate("ABC-123")).thenThrow(new RuntimeException("DB error"));

        ApiResponse<VehicleResponse> response = vehicleService.registerVehicle(request);

        assertEquals(SmartParkConstants.STATUS_ERROR, response.getStatus());
        assertEquals(SmartParkConstants.CODE_SYSTEM_ERROR, response.getCode());
    }

    @Test
    void testCheckIn_Success() {
        vehicle.setCheckinTime(null);
        vehicle.setCheckoutTime(LocalDateTime.now());
        when(vehicleRepository.findByLicensePlate("ABC-123")).thenReturn(Optional.of(vehicle));
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        ApiResponse<VehicleResponse> response = vehicleService.checkIn("LOT-01", "ABC-123");

        assertEquals(SmartParkConstants.STATUS_SUCCESS, response.getStatus());
        assertEquals(SmartParkConstants.SUCCESS_CHECKIN, response.getMessage());
    }

    @Test
    void testCheckIn_AlreadyCheckedIn() {
        vehicle.setCheckinTime(LocalDateTime.now());
        vehicle.setCheckoutTime(null);
        when(vehicleRepository.findByLicensePlate("ABC-123")).thenReturn(Optional.of(vehicle));

        ApiResponse<VehicleResponse> response = vehicleService.checkIn("LOT-01", "ABC-123");

        assertEquals(SmartParkConstants.STATUS_ERROR, response.getStatus());
        assertEquals(SmartParkConstants.CODE_VEHICLE_ALREADY_CHECKED_IN, response.getCode());
    }

    @Test
    void testCheckOut_Success() {
        vehicle.setCheckinTime(LocalDateTime.now());
        vehicle.setCheckoutTime(null);
        when(vehicleRepository.findByLicensePlate("ABC-123")).thenReturn(Optional.of(vehicle));
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        ApiResponse<VehicleResponse> response = vehicleService.checkOut("LOT-01", "ABC-123");

        assertEquals(SmartParkConstants.STATUS_SUCCESS, response.getStatus());
        assertEquals(SmartParkConstants.SUCCESS_CHECKOUT, response.getMessage());
    }

    @Test
    void testCheckOut_NoCheckIn() {
        vehicle.setCheckinTime(null);
        when(vehicleRepository.findByLicensePlate("ABC-123")).thenReturn(Optional.of(vehicle));

        ApiResponse<VehicleResponse> response = vehicleService.checkOut("LOT-01", "ABC-123");

        assertEquals(SmartParkConstants.STATUS_ERROR, response.getStatus());
        assertEquals(SmartParkConstants.CODE_VEHICLE_NO_CHECKIN, response.getCode());
    }

    @Test
    void testCheckOut_AlreadyCheckedOut() {
        vehicle.setCheckinTime(LocalDateTime.now());
        vehicle.setCheckoutTime(LocalDateTime.now());
        when(vehicleRepository.findByLicensePlate("ABC-123")).thenReturn(Optional.of(vehicle));

        ApiResponse<VehicleResponse> response = vehicleService.checkOut("LOT-01", "ABC-123");

        assertEquals(SmartParkConstants.STATUS_ERROR, response.getStatus());
        assertEquals(SmartParkConstants.CODE_VEHICLE_ALREADY_CHECKED_OUT, response.getCode());
    }

}

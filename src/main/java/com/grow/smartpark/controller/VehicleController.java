package com.grow.smartpark.controller;

import com.grow.smartpark.domain.ApiResponse;
import com.grow.smartpark.domain.VehicleRequest;
import com.grow.smartpark.domain.VehicleResponse;
import com.grow.smartpark.service.IVehicleService;

import lombok.extern.log4j.Log4j2;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2(topic = "vehicle")
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final IVehicleService vehicleService;

    public VehicleController(IVehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<VehicleResponse>> registerVehicle(@RequestBody VehicleRequest request) {
        log.info("Calling registerVehicle with request: {}", request);
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(vehicleService.registerVehicle(request));
        } catch (Exception ex) {
            log.error("Error registering vehicle", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }finally {
            log.info("Closing vehicle registration process");
        }
    }

    @PostMapping("/{lotId}/checkin/{licensePlate}")
    public ResponseEntity<ApiResponse<VehicleResponse>> checkIn(@PathVariable String lotId, @PathVariable String licensePlate) {
        log.info("Calling checkIn for lotId: {} and licensePlate: {}", lotId, licensePlate);
        try {
            return ResponseEntity.ok(vehicleService.checkIn(lotId, licensePlate));
        } catch (Exception ex) {
            log.error("Error checking in vehicle", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } finally {
            log.info("Closing vehicle check-in process for license plate: {}", licensePlate);
        }
    }

    @PostMapping("/{lotId}/checkout/{licensePlate}")
    public ResponseEntity<ApiResponse<VehicleResponse>> checkOut(@PathVariable String lotId, @PathVariable String licensePlate) {
        log.info("Calling checkOut for lotId: {} and licensePlate: {}", lotId, licensePlate);
        try {
            return ResponseEntity.ok(vehicleService.checkOut(lotId, licensePlate));
        } catch (Exception ex) {
            log.error("Error checking out vehicle", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } finally {
            log.info("Closing vehicle check-out process for license plate: {}", licensePlate);
        }
    }
}

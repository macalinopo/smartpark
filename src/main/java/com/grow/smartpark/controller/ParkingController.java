package com.grow.smartpark.controller;

import com.grow.smartpark.domain.ApiResponse;
import com.grow.smartpark.domain.ParkingLotRequest;
import com.grow.smartpark.domain.ParkingLotResponse;
import com.grow.smartpark.domain.VehicleResponse;
import lombok.extern.log4j.Log4j2;
import com.grow.smartpark.service.IParkingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking")
@Log4j2(topic = "parkinglot")
public class ParkingController {

    private final IParkingService parkingService;

    public ParkingController(IParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<ParkingLotResponse>> registerLot(@RequestBody ParkingLotRequest request) {
        log.info("Calling registerLot with request: {}", request);
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(parkingService.registerLot(request));
        } catch (Exception ex) {
            log.error("Error registering parking lot", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } finally {
            log.info("Closing parking lot registration process");
        }
    }

    @GetMapping("/{lotId}/status")
    public ResponseEntity<ApiResponse<ParkingLotResponse>> getLotStatus(@PathVariable String lotId) {
        log.info("Calling getLotStatus for lotId: {}", lotId);
        try {
            return ResponseEntity.ok(parkingService.getLotStatus(lotId));
        } catch (Exception ex) {
            log.error("Error retrieving parking lot status", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } finally {
            log.info("Closing parking lot status retrieval process for lotId: {}", lotId);
        }
    }

    @GetMapping("/{lotId}/vehicles")
    public ResponseEntity<ApiResponse<List<VehicleResponse>>> getVehiclesInLot(@PathVariable String lotId) {
        log.info("Calling getVehiclesInLot for lotId: {}", lotId);
        try {
            return ResponseEntity.ok(parkingService.getVehiclesInLot(lotId));
        } catch (Exception ex) {
            log.error("Error retrieving vehicles in parking lot", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } finally {
            log.info("Closing vehicle retrieval process for lotId: {}", lotId);
        }
    }
}

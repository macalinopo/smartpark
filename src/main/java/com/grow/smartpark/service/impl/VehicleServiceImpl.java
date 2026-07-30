package com.grow.smartpark.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.grow.smartpark.constants.SmartParkConstants;
import com.grow.smartpark.domain.ApiResponse;
import com.grow.smartpark.domain.VehicleRequest;
import com.grow.smartpark.domain.VehicleResponse;
import com.grow.smartpark.mapper.VehicleMapper;
import com.grow.smartpark.model.Vehicle;
import com.grow.smartpark.repository.VehicleRepository;
import com.grow.smartpark.service.IVehicleService;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Log4j2(topic = "vehicle")
public class VehicleServiceImpl implements IVehicleService {

    private final VehicleRepository vehicleRepository;
    private final ObjectMapper objectMapper;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
        this.objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule()) // 👈 support for LocalDateTime
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // 👈 ISO-8601 format
    }

    @Override
    public ApiResponse<VehicleResponse> registerVehicle(VehicleRequest request) {
        ApiResponse<VehicleResponse> response;
        try {
            log.info("Registering vehicle with license plate: {}", request.getLicensePlate());

            if (vehicleRepository.findByLicensePlate(request.getLicensePlate()).isPresent()) {
                response = ApiResponse.<VehicleResponse>builder()
                        .status(SmartParkConstants.STATUS_ERROR)
                        .code(SmartParkConstants.CODE_VEHICLE_DUPLICATE)
                        .message(SmartParkConstants.VEHICLE_ALREADY_EXISTS_MSG)
                        .data(null)
                        .build();
            } else {
                Vehicle vehicle = VehicleMapper.toEntity(request);
                vehicle.setRegisteredAt(LocalDateTime.now());
                Vehicle saved = vehicleRepository.save(vehicle);

                response = ApiResponse.<VehicleResponse>builder()
                        .status(SmartParkConstants.STATUS_SUCCESS)
                        .code(SmartParkConstants.CODE_SUCCESS)
                        .message(SmartParkConstants.SUCCESS_VEHICLE_REGISTERED)
                        .data(VehicleMapper.toResponse(saved))
                        .build();
            }
        } catch (Exception ex) {
            log.error("Error registering vehicle", ex);
            response = ApiResponse.<VehicleResponse>builder()
                    .status(SmartParkConstants.STATUS_ERROR)
                    .code(SmartParkConstants.CODE_SYSTEM_ERROR)
                    .message("Error registering vehicle")
                    .data(null)
                    .build();
        } finally {
            log.info("Vehicle registration request ended for license plate: {}", request.getLicensePlate());
        }

        logResponse(response);
        return response;
    }

    @Override
    public ApiResponse<VehicleResponse> checkIn(String lotId, String licensePlate) {
        log.info("Checking in vehicle with license plate: {} to lot ID: {}", licensePlate, lotId);
        ApiResponse<VehicleResponse> response;

        try {
            Vehicle vehicle = vehicleRepository.findByLicensePlate(licensePlate)
                    .orElseThrow(() -> new RuntimeException(SmartParkConstants.VEHICLE_NOT_FOUND_MSG));

            if (vehicle.getCheckinTime() != null && vehicle.getCheckoutTime() == null) {
                log.warn("Vehicle with license plate: {} is already checked in", licensePlate);
                response = ApiResponse.<VehicleResponse>builder()
                        .status(SmartParkConstants.STATUS_ERROR)
                        .code(SmartParkConstants.CODE_VEHICLE_ALREADY_CHECKED_IN)
                        .message(SmartParkConstants.VEHICLE_ALREADY_CHECKED_IN_MSG)
                        .data(null)
                        .build();
                log.info("Check-in attempt failed for vehicle with license plate: {} to lot ID: {}", licensePlate, lotId);
            } else {
                log.info("Vehicle with license plate: {} is being checked in to lot ID: {}", licensePlate, lotId);
                vehicle.setCheckinTime(LocalDateTime.now());
                vehicle.setCheckoutTime(null);
                Vehicle saved = vehicleRepository.save(vehicle);

                response = ApiResponse.<VehicleResponse>builder()
                        .status(SmartParkConstants.STATUS_SUCCESS)
                        .code(SmartParkConstants.CODE_SUCCESS)
                        .message(SmartParkConstants.SUCCESS_CHECKIN)
                        .data(VehicleMapper.toResponse(saved))
                        .build();
                log.info("Vehicle with license plate: {} successfully checked in to lot ID: {}", licensePlate, lotId);
            }
        } catch (Exception ex) {
            log.error("Error checking in vehicle", ex);
            response = ApiResponse.<VehicleResponse>builder()
                    .status(SmartParkConstants.STATUS_ERROR)
                    .code(SmartParkConstants.CODE_SYSTEM_ERROR)
                    .message("Error checking in vehicle")
                    .data(null)
                    .build();
            log.info("Check-in attempt failed for vehicle with license plate: {} to lot ID: {}", licensePlate, lotId);
        }

        logResponse(response);
        return response;
    }

    @Override
    public ApiResponse<VehicleResponse> checkOut(String lotId, String licensePlate) {
        log.info("Starting check-out process for vehicle with license plate: {} from lot ID: {}", licensePlate, lotId);
        ApiResponse<VehicleResponse> response;

        try {
            Vehicle vehicle = vehicleRepository.findByLicensePlate(licensePlate)
                    .orElseThrow(() -> new RuntimeException(SmartParkConstants.VEHICLE_NOT_FOUND_MSG));

            if (vehicle.getCheckinTime() == null) {
                log.warn("Vehicle with license plate: {} has not checked in", licensePlate);
                response = ApiResponse.<VehicleResponse>builder()
                        .status(SmartParkConstants.STATUS_ERROR)
                        .code(SmartParkConstants.CODE_VEHICLE_NO_CHECKIN)
                        .message(SmartParkConstants.VEHICLE_NO_CHECKIN_MSG)
                        .data(null)
                        .build();
                log.info("Check-out attempt failed for vehicle with license plate: {} from lot ID: {}", licensePlate, lotId);
            } else if (vehicle.getCheckoutTime() != null) {
                log.warn("Vehicle with license plate: {} has already checked out", licensePlate);
                response = ApiResponse.<VehicleResponse>builder()
                        .status(SmartParkConstants.STATUS_ERROR)
                        .code(SmartParkConstants.CODE_VEHICLE_ALREADY_CHECKED_OUT)
                        .message(SmartParkConstants.VEHICLE_ALREADY_CHECKED_OUT_MSG)
                        .data(null)
                        .build();
                log.info("Check-out attempt failed for vehicle with license plate: {} from lot ID: {}", licensePlate, lotId);
            } else {
                vehicle.setCheckoutTime(LocalDateTime.now());
                Vehicle saved = vehicleRepository.save(vehicle);
                log.info("Vehicle with license plate: {} successfully checked out from lot ID: {}", licensePlate, lotId);
                response = ApiResponse.<VehicleResponse>builder()
                        .status(SmartParkConstants.STATUS_SUCCESS)
                        .code(SmartParkConstants.CODE_SUCCESS)
                        .message(SmartParkConstants.SUCCESS_CHECKOUT)
                        .data(VehicleMapper.toResponse(saved))
                        .build();
                log.info("Check-out process completed for vehicle with license plate: {} from lot ID: {}", licensePlate, lotId);
            }
        } catch (Exception ex) {
            log.error("Error checking out vehicle", ex);
            response = ApiResponse.<VehicleResponse>builder()
                    .status(SmartParkConstants.STATUS_ERROR)
                    .code(SmartParkConstants.CODE_SYSTEM_ERROR)
                    .message("Error checking out vehicle")
                    .data(null)
                    .build();
            log.info("Check-out attempt failed for vehicle with license plate: {} from lot ID: {}", licensePlate, lotId);
        }

        logResponse(response);
        return response;
    }

    private void logResponse(Object response) {
        try {
            log.info("API Response JSON: {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));
        } catch (Exception e) {
            log.error("Failed to serialize API response", e);
        }
    }
}

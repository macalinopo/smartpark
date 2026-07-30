package com.grow.smartpark.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.grow.smartpark.constants.SmartParkConstants;
import com.grow.smartpark.utility.ValidationUtil;
import com.grow.smartpark.domain.ApiResponse;
import com.grow.smartpark.domain.ParkingLotRequest;
import com.grow.smartpark.domain.ParkingLotResponse;
import com.grow.smartpark.domain.VehicleResponse;
import com.grow.smartpark.mapper.ParkingLotMapper;
import com.grow.smartpark.mapper.VehicleMapper;
import com.grow.smartpark.model.ParkingLot;
import com.grow.smartpark.repository.ParkingLotRepository;
import com.grow.smartpark.service.IParkingService;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2(topic = "parkinglot")
public class ParkingServiceImpl implements IParkingService {

    private final ParkingLotRepository parkingLotRepository;
    private final ValidationUtil validationUtil;
    private final ObjectMapper objectMapper;

    public ParkingServiceImpl(ParkingLotRepository parkingLotRepository, ValidationUtil validationUtil) {
        this.parkingLotRepository = parkingLotRepository;
        this.validationUtil = validationUtil;
        this.objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    public ApiResponse<ParkingLotResponse> registerLot(ParkingLotRequest request) {
        log.info("Validating lot ID: {}", request.getLotId());
        ApiResponse<ParkingLotResponse> response;
        try {
            if (!validationUtil.isValidLotId(request.getLotId())) {
                log.warn("Invalid lot ID format or length exceeded: {}", request.getLotId());
                response = ApiResponse.<ParkingLotResponse>builder()
                        .status(SmartParkConstants.STATUS_ERROR)
                        .code("PL02")
                        .message("Invalid lot ID format or length exceeded.")
                        .data(null)
                        .build();
            } else if (parkingLotRepository.findByLotId(request.getLotId()).isPresent()) {
                log.warn("Duplicate lot ID: {}", request.getLotId());
                response = ApiResponse.<ParkingLotResponse>builder()
                        .status(SmartParkConstants.STATUS_ERROR)
                        .code(SmartParkConstants.CODE_LOT_DUPLICATE)
                        .message(SmartParkConstants.LOT_ALREADY_EXISTS_MSG)
                        .data(null)
                        .build();
            } else {
                log.info("Registering new lot with ID: {}", request.getLotId());
                ParkingLot lot = ParkingLotMapper.toEntity(request);
                ParkingLot savedLot = parkingLotRepository.save(lot);
                ParkingLotResponse lotResponse = ParkingLotMapper.toResponse(savedLot);

                response = ApiResponse.<ParkingLotResponse>builder()
                        .status(SmartParkConstants.STATUS_SUCCESS)
                        .code(SmartParkConstants.CODE_SUCCESS)
                        .message(SmartParkConstants.SUCCESS_LOT_REGISTERED)
                        .data(lotResponse)
                        .build();
            }
        } catch (Exception ex) {
            log.error("Error registering lot", ex);
            response = ApiResponse.<ParkingLotResponse>builder()
                    .status(SmartParkConstants.STATUS_ERROR)
                    .code(SmartParkConstants.CODE_SYSTEM_ERROR)
                    .message("Error registering lot")
                    .data(null)
                    .build();
        } finally {
            log.info("Registration request ended for lot ID: {}", request.getLotId());
        }

        logResponse(response);
        return response;
    }

    @Override
    public ApiResponse<ParkingLotResponse> getLotStatus(String lotId) {
        log.info("Retrieving status for lot ID: {}", lotId);
        ParkingLot lot = parkingLotRepository.findByLotId(lotId)
                .orElseThrow(() -> new RuntimeException(SmartParkConstants.LOT_NOT_FOUND_MSG));

        ParkingLotResponse lotResponse = ParkingLotMapper.toResponse(lot);
        ApiResponse<ParkingLotResponse> response = ApiResponse.<ParkingLotResponse>builder()
                .status(SmartParkConstants.STATUS_SUCCESS)
                .code(SmartParkConstants.CODE_SUCCESS)
                .message("Lot status retrieved successfully.")
                .data(lotResponse)
                .build();

        logResponse(response);
        return response;
    }

    @Override
    public ApiResponse<List<VehicleResponse>> getVehiclesInLot(String lotId) {
        log.info("Retrieving vehicles for lot ID: {}", lotId);
        ParkingLot lot = parkingLotRepository.findByLotId(lotId)
                .orElseThrow(() -> new RuntimeException(SmartParkConstants.LOT_NOT_FOUND_MSG));

        List<VehicleResponse> vehicles = lot.getVehicles().stream()
                .map(VehicleMapper::toResponse)
                .collect(Collectors.toList());

        ApiResponse<List<VehicleResponse>> response = ApiResponse.<List<VehicleResponse>>builder()
                .status(SmartParkConstants.STATUS_SUCCESS)
                .code(SmartParkConstants.CODE_SUCCESS)
                .message("Vehicles retrieved successfully.")
                .data(vehicles)
                .build();

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

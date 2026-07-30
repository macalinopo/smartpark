package com.grow.smartpark.mapper;

import com.grow.smartpark.model.ParkingLot;
import com.grow.smartpark.domain.ParkingLotRequest;
import com.grow.smartpark.domain.ParkingLotResponse;
import java.util.stream.Collectors;

public class ParkingLotMapper {

    // Request DTO → Entity
    public static ParkingLot toEntity(ParkingLotRequest request) {
        ParkingLot lot = new ParkingLot();
        lot.setLotId(request.getLotId());
        lot.setLocation(request.getLocation());
        lot.setCapacity(request.getCapacity());
        lot.setAvailableSpaces(request.getAvailableSpaces());
        return lot;
    }

    // Entity → Response DTO
    public static ParkingLotResponse toResponse(ParkingLot lot) {
        return ParkingLotResponse.builder()
                .lotId(lot.getLotId())
                .location(lot.getLocation())
                .capacity(lot.getCapacity())
                .availableSpaces(lot.getAvailableSpaces())
                .occupiedSpaces(lot.getCapacity() - lot.getAvailableSpaces())
                .vehicles(lot.getVehicles() != null
                        ? lot.getVehicles().stream()
                            .map(VehicleMapper::toResponse)
                            .collect(Collectors.toList())
                        : null)
                .createdAt(lot.getCreatedAt())
                .updatedAt(lot.getUpdatedAt())
                .build();
    }
}

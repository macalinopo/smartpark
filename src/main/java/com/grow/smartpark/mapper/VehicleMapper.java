package com.grow.smartpark.mapper;

import com.grow.smartpark.model.Vehicle;
import com.grow.smartpark.domain.VehicleRequest;
import com.grow.smartpark.domain.VehicleResponse;

public class VehicleMapper {

    // Request DTO → Entity
    public static Vehicle toEntity(VehicleRequest request) {
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate(request.getLicensePlate());
        vehicle.setVehicleType(request.getVehicleType());
        vehicle.setOwnerName(request.getOwnerName());
        return vehicle;
    }

    // Entity → Response DTO
    public static VehicleResponse toResponse(Vehicle vehicle) {
        return VehicleResponse.builder()
                .licensePlate(vehicle.getLicensePlate())
                .vehicleType(vehicle.getVehicleType())
                .ownerName(vehicle.getOwnerName())
                .lotId(vehicle.getParkingLot() != null ? vehicle.getParkingLot().getLotId() : null)
                .registeredAt(vehicle.getRegisteredAt())
                .checkinTime(vehicle.getCheckinTime())
                .checkoutTime(vehicle.getCheckoutTime())
                .build();
    }
}

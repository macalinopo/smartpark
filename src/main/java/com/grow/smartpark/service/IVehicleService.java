package com.grow.smartpark.service;

import com.grow.smartpark.domain.ApiResponse;
import com.grow.smartpark.domain.VehicleRequest;
import com.grow.smartpark.domain.VehicleResponse;

public interface IVehicleService {

 ApiResponse<VehicleResponse> registerVehicle(VehicleRequest request);
    ApiResponse<VehicleResponse> checkIn(String lotId, String licensePlate);
    ApiResponse<VehicleResponse> checkOut(String lotId, String licensePlate);
}

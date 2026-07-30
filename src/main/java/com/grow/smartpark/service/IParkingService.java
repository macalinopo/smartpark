package com.grow.smartpark.service;
import java.util.List;

import com.grow.smartpark.domain.ApiResponse;
import com.grow.smartpark.domain.ParkingLotRequest;
import com.grow.smartpark.domain.ParkingLotResponse;
import com.grow.smartpark.domain.VehicleResponse;

public interface IParkingService {
ApiResponse<ParkingLotResponse> registerLot(ParkingLotRequest request);
    ApiResponse<ParkingLotResponse> getLotStatus(String lotId);
    ApiResponse<List<VehicleResponse>> getVehiclesInLot(String lotId);

}

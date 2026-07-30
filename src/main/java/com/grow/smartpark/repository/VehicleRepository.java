package com.grow.smartpark.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.grow.smartpark.model.Vehicle;
import java.util.List;
import java.util.Optional;  

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findByLicensePlate(String licensePlate);
    List<Vehicle> findByParkingLot_LotId(String lotId);
}

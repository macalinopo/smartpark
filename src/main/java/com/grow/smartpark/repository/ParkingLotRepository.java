package com.grow.smartpark.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.grow.smartpark.model.ParkingLot;
import java.util.Optional;


public interface ParkingLotRepository extends JpaRepository<ParkingLot, String> {

    Optional<ParkingLot> findByLotId(String lotId);

}

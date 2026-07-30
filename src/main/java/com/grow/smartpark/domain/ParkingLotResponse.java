package com.grow.smartpark.domain;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParkingLotResponse {
    
    private String lotId;
    private String location;
    private Integer capacity;
    private Integer availableSpaces;
    private Integer occupiedSpaces;
    private List<VehicleResponse> vehicles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

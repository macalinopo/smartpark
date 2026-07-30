package com.grow.smartpark.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleResponse {

    private String licensePlate;
    private String vehicleType;
    private String ownerName;
    private String lotId;
    private LocalDateTime registeredAt;
    private LocalDateTime checkinTime;
    private LocalDateTime checkoutTime;
    
}

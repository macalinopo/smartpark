package com.grow.smartpark.domain;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ParkingLotRequest {

    @NotNull
    @Schema(description = "Name of the parking lot", example = "Downtown Parking")
    private String lotId;

    @NotNull
    @Schema(description = "Location of the parking lot", example = "123 Main St, Cityville")
    private String location;

    @NotNull
    @Schema(description = "Capacity of the parking lot", example = "100")
    private Integer capacity;

    @NotNull
    @Schema(description = "Available spaces in the parking lot", example = "50")
    private Integer availableSpaces;

    @NotNull
    @Schema(description = "List of vehicles in the parking lot")
    private List<VehicleRequest> vehicles;

    
}

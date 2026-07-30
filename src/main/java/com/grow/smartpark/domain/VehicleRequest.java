package com.grow.smartpark.domain;

import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class VehicleRequest {

    @NotNull
    @Schema(description = "License plate of the vehicle", example = "ABC-123")
    private String licensePlate;

    @NotNull
    @Schema(description = "Type of the vehicle", example = "Car")
    private String vehicleType;

    @NotNull
    @Schema(description = "Owner name of the vehicle", example = "John Doe")
    private String ownerName;
}

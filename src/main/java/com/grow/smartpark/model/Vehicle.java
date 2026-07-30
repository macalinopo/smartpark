package com.grow.smartpark.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "license_plate")
    private String licensePlate;

    @Column(name = "vehicle_type")
    private String vehicleType;

    @Column(name = "owner_name")
    private String ownerName;

    @ManyToOne
    @JoinColumn(name = "parking_lot_id")
    private ParkingLot parkingLot;

    @Column(name = "registered_at", updatable = false)
    private LocalDateTime registeredAt;

    @Column(name = "checkin_time")
    private LocalDateTime checkinTime;

    @Column(name = "checkout_time")
    private LocalDateTime checkoutTime;

    @PrePersist
    protected void onRegister() {
        registeredAt = LocalDateTime.now();
    }
}

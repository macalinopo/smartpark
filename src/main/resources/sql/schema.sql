CREATE TABLE parking_lots (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    lot_id VARCHAR(50) UNIQUE NOT NULL,
    location VARCHAR(255) NOT NULL,
    capacity INT NOT NULL,
    available_spaces INT NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE vehicles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    license_plate VARCHAR(50) UNIQUE NOT NULL,
    vehicle_type VARCHAR(50) NOT NULL,
    owner_name VARCHAR(100) NOT NULL,
    registered_at TIMESTAMP,
    checkin_time TIMESTAMP,
    checkout_time TIMESTAMP,
    parking_lot_id BIGINT,
    FOREIGN KEY (parking_lot_id) REFERENCES parking_lots(id)
);

CREATE TABLE validation_rules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rule_name VARCHAR(100) UNIQUE NOT NULL,
    rule_value VARCHAR(255) NOT NULL,
    description VARCHAR(255)
);

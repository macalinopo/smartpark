-- Parking lots
CREATE TABLE parking_lots (
    lot_id VARCHAR(50) PRIMARY KEY,
    location VARCHAR(255),
    capacity INT,
    available_spaces INT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

INSERT INTO parking_lots (lot_id, location, capacity, available_spaces, created_at, updated_at)
VALUES ('LOT123', 'Downtown', 50, 50, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO parking_lots (lot_id, location, capacity, available_spaces, created_at, updated_at)
VALUES ('LOT456', 'Airport', 100, 100, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Vehicles
CREATE TABLE vehicles (
    license_plate VARCHAR(20) PRIMARY KEY,
    vehicle_type VARCHAR(50),
    owner_name VARCHAR(100),
    registered_at TIMESTAMP,
    parking_lot_id VARCHAR(50),
    CONSTRAINT chk_license_plate_format CHECK (license_plate REGEXP '^[A-Z0-9-]+$'),
    CONSTRAINT chk_owner_name_format CHECK (owner_name REGEXP '^[A-Za-z ]+$'),
    CONSTRAINT fk_parking_lot FOREIGN KEY (parking_lot_id) REFERENCES parking_lots(lot_id)
);

INSERT INTO vehicles (license_plate, vehicle_type, owner_name, registered_at, parking_lot_id)
VALUES ('ABC-123', 'Car', 'John Doe', CURRENT_TIMESTAMP, NULL);

INSERT INTO vehicles (license_plate, vehicle_type, owner_name, registered_at, parking_lot_id)
VALUES ('XYZ-789', 'Motorcycle', 'Jane Smith', CURRENT_TIMESTAMP, NULL);

CREATE TABLE validation_rules (
    rule_name VARCHAR(50) PRIMARY KEY,
    rule_value VARCHAR(255),
    description VARCHAR(255)
);

INSERT INTO validation_rules (rule_name, rule_value, description)
VALUES ('LICENSE_PLATE_REGEX', '^[A-Z0-9-]+$', 'Regex for license plate format');

INSERT INTO validation_rules (rule_name, rule_value, description)
VALUES ('OWNER_NAME_REGEX', '^[A-Za-z ]+$', 'Regex for owner name format');

INSERT INTO validation_rules (rule_name, rule_value, description)
VALUES ('LOT_ID_MAX_LENGTH', '50', 'Maximum length for lot ID');

-- Clear child tables first, then parent table
DELETE FROM vehicles;
DELETE FROM validation_rules;
DELETE FROM parking_lots;

INSERT INTO parking_lots (lot_id, location, capacity, available_spaces, created_at, updated_at)
VALUES ('LOT123', 'Downtown', 50, 50, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO parking_lots (lot_id, location, capacity, available_spaces, created_at, updated_at)
VALUES ('LOT456', 'Airport', 100, 100, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO vehicles (license_plate, vehicle_type, owner_name, registered_at, parking_lot_id)
VALUES ('ABC-123', 'Car', 'John Doe', CURRENT_TIMESTAMP, NULL);

INSERT INTO vehicles (license_plate, vehicle_type, owner_name, registered_at, parking_lot_id)
VALUES ('XYZ-789', 'Motorcycle', 'Jane Smith', CURRENT_TIMESTAMP, NULL);

INSERT INTO validation_rules (rule_name, rule_value, description)
VALUES ('LICENSE_PLATE_REGEX', '^[A-Z0-9-]+$', 'Regex for license plate format');

INSERT INTO validation_rules (rule_name, rule_value, description)
VALUES ('OWNER_NAME_REGEX', '^[A-Za-z ]+$', 'Regex for owner name format');

INSERT INTO validation_rules (rule_name, rule_value, description)
VALUES ('LOT_ID_MAX_LENGTH', '50', 'Maximum length for lot ID');

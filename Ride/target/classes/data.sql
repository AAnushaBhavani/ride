-- Sample data for testing (optional)
-- This file will be executed automatically when the application starts

-- Insert sample users
INSERT INTO users (name, email, phone, password, role, car_model, license_plate, car_capacity, created_at, rating) VALUES
('John Driver', 'driver@example.com', '1234567890', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', 'DRIVER', 'Toyota Camry', 'ABC123', 4, NOW(), 4.5),
('Jane Passenger', 'passenger@example.com', '0987654321', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', 'PASSENGER', NULL, NULL, NULL, NOW(), 0.0);

-- Insert sample rides (will be available after users are created)
-- Note: These are commented out as they require actual user IDs from the database
-- INSERT INTO rides (driver_id, source, destination, departure_date, departure_time, total_seats, available_seats, price, vehicle_type, status, created_at) VALUES
-- (1, 'New York', 'Boston', '2024-01-15 10:00:00', '2024-01-15 10:00:00', 4, 3, 50.0, 'Sedan', 'ACTIVE', NOW()),
-- (1, 'Boston', 'Philadelphia', '2024-01-16 14:00:00', '2024-01-16 14:00:00', 4, 4, 40.0, 'Sedan', 'ACTIVE', NOW());

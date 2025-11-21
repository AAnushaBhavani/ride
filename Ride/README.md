in# Ride Sharing Application

A comprehensive ride-sharing platform built with Spring Boot, featuring user management, ride posting, and booking functionality.

## Features

### User Management
- **Registration & Login**: Secure user registration and login via email
- **Password Encryption**: BCrypt password hashing for security
- **Role-based Access**: Driver and Passenger roles with different permissions
- **User Profiles**: 
  - Passengers: name, contact, booking history
  - Drivers: name, contact, vehicle details (car model, license plate, capacity)

### Authentication & Security
- **Spring Security + JWT**: Token-based authentication
- **Session Management**: Stateless JWT tokens
- **Role Authorization**: Method-level security with @PreAuthorize

### Ride Management (Drivers)
- **Post Rides**: Create rides with source, destination, date, time
- **Vehicle Details**: Auto-filled from driver profile
- **Seat Management**: Track available seats
- **Ride Status**: Active, Completed, Cancelled

### Ride Search & Booking (Passengers)
- **Search Rides**: By source, destination, date
- **Filter Options**: Price range, vehicle type, driver rating
- **Seat Booking**: Book multiple seats with passenger details
- **Booking History**: View all bookings and their status

### Booking System
- **Confirmation Process**: Drivers confirm passenger bookings
- **Seat Updates**: Automatic seat availability reduction
- **Booking Status**: Pending, Confirmed, Cancelled, Completed
- **Dashboard Views**: Separate views for drivers and passengers

## Technology Stack

- **Backend**: Spring Boot 3.2.0
- **Database**: MySQL with JPA/Hibernate
- **Security**: Spring Security with JWT
- **Password Encryption**: BCrypt
- **Build Tool**: Maven
- **Java Version**: 17

## API Endpoints

### Authentication
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login
- `GET /api/auth/me` - Get current user info

### Rides
- `POST /api/rides` - Create a ride (Driver only)
- `POST /api/rides/search` - Search rides
- `GET /api/rides/{id}` - Get ride details
- `GET /api/rides/my-rides` - Get driver's rides
- `PUT /api/rides/{id}` - Update ride (Driver only)
- `PUT /api/rides/{id}/cancel` - Cancel ride (Driver only)
- `GET /api/rides/{id}/available` - Check available seats

### Bookings
- `POST /api/bookings` - Create booking (Passenger only)
- `PUT /api/bookings/{id}/confirm` - Confirm booking (Driver only)
- `PUT /api/bookings/{id}/cancel` - Cancel booking
- `GET /api/bookings/my-bookings` - Get passenger's bookings
- `GET /api/bookings/driver-bookings` - Get driver's bookings
- `GET /api/bookings/driver-bookings/pending` - Get pending bookings (Driver)
- `GET /api/bookings/driver-bookings/confirmed` - Get confirmed bookings (Driver)

## Database Schema

### Users Table
- id, email, password, name, phone, role, profile_image, rating, created_at
- Driver fields: car_model, license_plate, car_capacity

### Rides Table
- id, driver_id, source, destination, departure_date, departure_time
- available_seats, total_seats, price, vehicle_type, description, status, created_at

### Bookings Table
- id, ride_id, passenger_id, seats_booked, total_price, status
- pickup_location, dropoff_location, special_requests, booking_time, confirmed_at

## Setup Instructions

### Prerequisites
- Java 17+
- Maven 3.6+
- MySQL 8.0+

### Database Setup
1. Create MySQL database named `ride_sharing`
2. Update database credentials in `application.properties`
3. Run the application - tables will be created automatically

### Running the Application
```bash
mvn clean install
mvn spring-boot:run
```

The application will start on `http://localhost:8080`c

## Sample API Usage

### Register a Driver
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Driver",
    "email": "driver@example.com",
    "phone": "1234567890",
    "password": "password123",
    "role": "DRIVER",
    "carModel": "Toyota Camry",
    "licensePlate": "ABC123",
    "carCapacity": 4
  }'
```

### Register a Passenger
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Jane Passenger",
    "email": "passenger@example.com",
    "phone": "0987654321",
    "password": "password123",
    "role": "PASSENGER"
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "driver@example.com",
    "password": "password123"
  }'
```

### Post a Ride (Driver)
```bash
curl -X POST http://localhost:8080/api/rides \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "source": "New York",
    "destination": "Boston",
    "departureDate": "2024-01-15T10:00:00",
    "departureTime": "2024-01-15T10:00:00",
    "totalSeats": 3,
    "price": 50.0,
    "vehicleType": "Sedan",
    "description": "Comfortable ride with AC"
  }'
```

### Search Rides
```bash
curl -X POST http://localhost:8080/api/rides/search \
  -H "Content-Type: application/json" \
  -d '{
    "source": "New York",
    "destination": "Boston",
    "date": "2024-01-15T00:00:00"
  }'
```

### Book a Ride (Passenger)
```bash
curl -X POST http://localhost:8080/api/bookings \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer PASSENGER_JWT_TOKEN" \
  -d '{
    "rideId": 1,
    "seatsBooked": 1,
    "pickupLocation": "Times Square",
    "dropoffLocation": "Central Station"
  }'
```

## Security Features

- **JWT Authentication**: Stateless token-based authentication
- **Password Encryption**: BCrypt hashing for secure password storage
- **Role-based Authorization**: Different permissions for drivers and passengers
- **Input Validation**: Comprehensive validation using Jakarta Bean Validation
- **CORS Configuration**: Configured for frontend integration

## Error Handling

The application provides comprehensive error handling with meaningful error messages:
- Validation errors return 400 with detailed field errors
- Authentication errors return 401/403 with appropriate messages
- Resource not found errors return 404
- Business logic violations return 400 with descriptive messages

## Future Enhancements

- Real-time notifications for booking updates
- Payment integration
- Rating system for drivers and passengers
- Route optimization
- Mobile app support
- Advanced analytics dashboard

package com.rideapp.service;

import com.rideapp.dto.RideDTO;
import com.rideapp.dto.UserDTO;
import com.rideapp.model.Ride;
import com.rideapp.model.User;
import com.rideapp.repository.RideRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RideService {

    private final RideRepository rideRepository;
    private final UserService userService;

    public List<RideDTO> getAllRides() {
        return rideRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Ride getRideById(Long id) {
        return rideRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ride not found with id: " + id));
    }

    public void updateAvailableSeats(Long rideId, int seats) {
        Ride ride = getRideById(rideId);
        int newSeats = ride.getAvailableSeats() + seats;

        if (newSeats < 0 || newSeats > ride.getTotalSeats()) {
            throw new IllegalArgumentException("Invalid seats");
        }

        ride.setAvailableSeats(newSeats);
        rideRepository.save(ride);
    }

    private RideDTO convertToDto(Ride ride) {
        RideDTO dto = new RideDTO();
        dto.setId(ride.getId());
        dto.setSource(ride.getSource());
        dto.setDestination(ride.getDestination());
        dto.setDepartureDate(ride.getDepartureDate().toLocalDate());
        dto.setDepartureTime(ride.getDepartureTime().toLocalTime());
        dto.setTotalSeats(ride.getTotalSeats());
        dto.setAvailableSeats(ride.getAvailableSeats());
        dto.setPrice(ride.getPrice());
        dto.setVehicleType(ride.getVehicleType());
        dto.setDescription(ride.getDescription());

        User driver = ride.getDriver();
        UserDTO driverDto = new UserDTO();
        driverDto.setId(driver.getId());
        driverDto.setName(driver.getName());
        driverDto.setEmail(driver.getEmail());
        driverDto.setPhone(driver.getPhone());
        driverDto.setRole(driver.getRole().name());

        dto.setDriver(driverDto);
        return dto;
    }
}

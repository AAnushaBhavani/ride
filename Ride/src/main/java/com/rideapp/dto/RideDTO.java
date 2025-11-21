// src/main/java/com/rideapp/dto/RideDTO.java
package com.rideapp.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import com.rideapp.dto.UserDTO;

@Data
public class RideDTO {
    private Long id;
    private String source;
    private String destination;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private Integer totalSeats;
    private Integer availableSeats;
    private Double price;
    private String vehicleType;
    private String description;
    private UserDTO driver;
}
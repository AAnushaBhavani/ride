package com.rideapp.controller;

import com.rideapp.dto.RideDTO;
import com.rideapp.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rides")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RideController {

    private final RideService rideService;

    @GetMapping
    public ResponseEntity<List<RideDTO>> getAllRides() {
        return ResponseEntity.ok(rideService.getAllRides());
    }
}

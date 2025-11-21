package com.rideapp.controller;

import com.rideapp.dto.BookingDTO;
import com.rideapp.dto.BookingRequest;
import com.rideapp.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@CrossOrigin(origins = "${cors.allowed-origins:*}")
@SecurityRequirement(name = "bearerAuth")
public class BookingController {

    private final BookingService bookingService;

    @GetMapping
    @PreAuthorize("hasAnyRole('PASSENGER','DRIVER')")
    @Operation(summary = "Get all bookings for the current user")
    public ResponseEntity<Page<BookingDTO>> getUserBookings(
            @RequestHeader("Authorization") String token,
            Pageable pageable) {
        
        return ResponseEntity.ok(bookingService.getUserBookings(token, pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PASSENGER','DRIVER')")
    @Operation(summary = "Get booking by ID")
    public ResponseEntity<BookingDTO> getBookingById(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {
        
        return ResponseEntity.ok(bookingService.getBookingById(id, token));
    }

    @PostMapping
    @PreAuthorize("hasRole('PASSENGER')")
    @Operation(summary = "Create a new booking (Passenger only)")
    public ResponseEntity<BookingDTO> createBooking(
            @Valid @RequestBody BookingRequest bookingRequest,
            @RequestHeader("Authorization") String token) {
        
        BookingDTO createdBooking = bookingService.createBooking(bookingRequest, token);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdBooking.getId())
                .toUri();
                
        return ResponseEntity.created(location).body(createdBooking);
    }

    @PutMapping("/{id}/cancel")
    @PreAuthorize("hasRole('PASSENGER')")
    @Operation(summary = "Cancel a booking (Passenger only)")
    public ResponseEntity<Void> cancelBooking(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {
        
        bookingService.cancelBooking(id, token);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ride/{rideId}")
    @PreAuthorize("hasAnyRole('DRIVER','PASSENGER')")
    @Operation(summary = "Get all bookings for a specific ride")
    public ResponseEntity<Page<BookingDTO>> getBookingsByRide(
            @PathVariable Long rideId,
            @RequestHeader("Authorization") String token,
            Pageable pageable) {
        
        return ResponseEntity.ok(bookingService.getBookingsByRide(rideId, token, pageable));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('DRIVER')")
    @Operation(summary = "Update booking status (Driver only)")
    public ResponseEntity<BookingDTO> updateBookingStatus(
            @PathVariable Long id,
            @RequestParam String status,
            @RequestHeader("Authorization") String token) {
        
        return ResponseEntity.ok(bookingService.updateBookingStatus(id, status, token));
    }
}
package com.rideapp.service;

import com.rideapp.dto.BookingDTO;
import com.rideapp.dto.BookingRequest;
import com.rideapp.model.Booking;
import com.rideapp.model.Ride;
import com.rideapp.model.User;
import com.rideapp.repository.BookingRepository;
import com.rideapp.repository.RideRepository;
import com.rideapp.repository.UserRepository;
import com.rideapp.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RideRepository rideRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public Page<BookingDTO> getUserBookings(String token, Pageable pageable) {
        String username = jwtUtil.extractUsername(token.replace("Bearer ", ""));
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        return bookingRepository.findByPassengerId(user.getId(), pageable)
                .map(Booking::toDTO);
    }

    public BookingDTO getBookingById(Long id, String token) {
        String username = jwtUtil.extractUsername(token.replace("Bearer ", ""));
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        return bookingRepository.findByIdAndPassengerId(id, user.getId())
                .map(Booking::toDTO)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    public BookingDTO createBooking(BookingRequest bookingRequest, String token) {
        String username = jwtUtil.extractUsername(token.replace("Bearer ", ""));
        User passenger = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        Ride ride = rideRepository.findById(bookingRequest.getRideId())
                .orElseThrow(() -> new RuntimeException("Ride not found"));
        
        if (ride.getAvailableSeats() < bookingRequest.getSeatsBooked()) {
            throw new RuntimeException("Not enough available seats");
        }
        
        Booking booking = new Booking();
        booking.setRide(ride);
        booking.setPassenger(passenger);
        booking.setSeatsBooked(bookingRequest.getSeatsBooked());
        booking.setTotalPrice(ride.getPrice() * bookingRequest.getSeatsBooked());
        booking.setPickupLocation(bookingRequest.getPickupLocation());
        booking.setDropoffLocation(bookingRequest.getDropoffLocation());
        booking.setSpecialRequests(bookingRequest.getSpecialRequests());
        booking.setStatus(Booking.BookingStatus.PENDING);
        
        // Update available seats
        ride.setAvailableSeats(ride.getAvailableSeats() - bookingRequest.getSeatsBooked());
        rideRepository.save(ride);
        
        return Booking.toDTO(bookingRepository.save(booking));
    }

    public void cancelBooking(Long id, String token) {
        String username = jwtUtil.extractUsername(token.replace("Bearer ", ""));
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
                
        if (!booking.getPassenger().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized to cancel this booking");
        }
        
        // Update available seats
        Ride ride = booking.getRide();
        ride.setAvailableSeats(ride.getAvailableSeats() + booking.getSeatsBooked());
        rideRepository.save(ride);
        
        booking.setStatus(Booking.BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }

    public Page<BookingDTO> getBookingsByRide(Long rideId, String token, Pageable pageable) {
        String username = jwtUtil.extractUsername(token.replace("Bearer ", ""));
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        // Verify user has access to this ride
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));
                
        if (!ride.getDriver().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized to view these bookings");
        }
        
        return bookingRepository.findByRideId(rideId, pageable)
                .map(Booking::toDTO);
    }

    public BookingDTO updateBookingStatus(Long id, String status, String token) {
        String username = jwtUtil.extractUsername(token.replace("Bearer ", ""));
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
                
        // Verify user is the driver of this ride
        if (!booking.getRide().getDriver().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized to update this booking");
        }
        
        try {
            Booking.BookingStatus newStatus = Booking.BookingStatus.valueOf(status.toUpperCase());
            booking.setStatus(newStatus);
            
            if (newStatus == Booking.BookingStatus.CONFIRMED) {
                booking.setConfirmedAt(java.time.LocalDateTime.now());
            } else if (newStatus == Booking.BookingStatus.REJECTED || 
                      newStatus == Booking.BookingStatus.CANCELLED) {
                // Return seats if booking is rejected or cancelled
                Ride ride = booking.getRide();
                ride.setAvailableSeats(ride.getAvailableSeats() + booking.getSeatsBooked());
                rideRepository.save(ride);
            }
            
            return Booking.toDTO(bookingRepository.save(booking));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + status);
        }
    }
}

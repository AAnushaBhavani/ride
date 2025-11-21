package com.rideapp.dto;

import com.rideapp.model.Booking;
import com.rideapp.model.Ride;
import com.rideapp.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private Long id;
    private Long rideId;
    private Long passengerId;
    private String passengerName;
    private String rideFrom;
    private String rideTo;
    private LocalDateTime rideDateTime;
    private Integer seatsBooked;
    private Double totalPrice;
    private String status;
    private String pickupLocation;
    private String dropoffLocation;
    private String specialRequests;
    private LocalDateTime bookingTime;
    private LocalDateTime confirmedAt;

    public static BookingDTO fromBooking(Booking booking) {
        if (booking == null) {
            return null;
        }
        
        BookingDTO dto = new BookingDTO();
        Ride ride = booking.getRide();
        User passenger = booking.getPassenger();

        dto.setId(booking.getId());
        dto.setRideId(ride != null ? ride.getId() : null);
        dto.setPassengerId(passenger != null ? passenger.getId() : null);
        dto.setPassengerName(passenger != null ? passenger.getName() : null);
        dto.setRideFrom(ride != null ? ride.getSource() : null);
        dto.setRideTo(ride != null ? ride.getDestination() : null);
        dto.setRideDateTime(ride != null ? ride.getDepartureTime() : null);
        dto.setSeatsBooked(booking.getSeatsBooked());
        dto.setTotalPrice(booking.getTotalPrice());
        dto.setStatus(booking.getStatus() != null ? booking.getStatus().name() : null);
        dto.setPickupLocation(booking.getPickupLocation());
        dto.setDropoffLocation(booking.getDropoffLocation());
        dto.setSpecialRequests(booking.getSpecialRequests());
        dto.setBookingTime(booking.getBookingTime());
        dto.setConfirmedAt(booking.getConfirmedAt());

        return dto;
    }
}
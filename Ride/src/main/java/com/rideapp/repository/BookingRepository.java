package com.rideapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.rideapp.model.Booking;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByPassengerId(Long passengerId);

    List<Booking> findByRideId(Long rideId);

    List<Booking> findByRideDriverId(Long driverId);

    Optional<Booking> findByRideIdAndPassengerId(Long rideId, Long passengerId);

    List<Booking> findByStatus(Booking.BookingStatus status);

    @Query("SELECT b FROM Booking b WHERE b.ride.id = :rideId AND b.status != 'CANCELLED'")
    List<Booking> findActiveBookingsByRideId(@Param("rideId") Long rideId);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.ride.id = :rideId AND b.status != 'CANCELLED'")
    Long countActiveBookingsByRideId(@Param("rideId") Long rideId);
}

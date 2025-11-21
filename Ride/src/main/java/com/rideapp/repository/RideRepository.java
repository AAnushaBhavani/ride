package com.rideapp.repository;

import com.rideapp.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {
    List<Ride> findByDriverId(Long driverId);
    
    List<Ride> findByStatus(Ride.RideStatus status);
    
    @Query("SELECT r FROM Ride r WHERE " +
           "r.source ILIKE %:source% AND " +
           "r.destination ILIKE %:destination% AND " +
           "r.departureDate BETWEEN :startDate AND :endDate AND " +
           "r.status = 'ACTIVE' AND " +
           "r.availableSeats > 0")
    List<Ride> searchRides(@Param("source") String source,
                          @Param("destination") String destination,
                          @Param("startDate") LocalDateTime startDate,
                          @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT r FROM Ride r WHERE " +
           "r.source ILIKE %:source% AND " +
           "r.destination ILIKE %:destination% AND " +
           "r.departureDate = :date AND " +
           "r.status = 'ACTIVE' AND " +
           "r.availableSeats > 0 " +
           "ORDER BY r.price ASC")
    List<Ride> searchRidesByDate(@Param("source") String source,
                                @Param("destination") String destination,
                                @Param("date") LocalDateTime date);
    
    @Query("SELECT r FROM Ride r WHERE " +
           "r.source ILIKE %:source% AND " +
           "r.destination ILIKE %:destination% AND " +
           "r.departureDate = :date AND " +
           "r.price BETWEEN :minPrice AND :maxPrice AND " +
           "r.status = 'ACTIVE' AND " +
           "r.availableSeats > 0")
    List<Ride> searchRidesWithFilters(@Param("source") String source,
                                      @Param("destination") String destination,
                                      @Param("date") LocalDateTime date,
                                      @Param("minPrice") Double minPrice,
                                      @Param("maxPrice") Double maxPrice);
}

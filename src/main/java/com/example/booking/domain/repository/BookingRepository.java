package com.example.booking.domain.repository;

import com.example.booking.domain.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query(value = "SELECT b FROM Booking b " +
            "WHERE b.property.id = :propertyId " +
            "AND b.startDate <= :endDate " +
            "AND b.endDate >= :startDate")
    List<Booking> findAllByPropertyAndByDateRangeOverlap(@Param("propertyId") Long propertyId,
                                                         @Param("startDate") LocalDate startDate,
                                                         @Param("endDate") LocalDate endDate);
}

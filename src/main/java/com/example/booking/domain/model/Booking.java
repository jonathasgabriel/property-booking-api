package com.example.booking.domain.model;

import com.example.booking.core.utils.DateRangeUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "bookings")
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    public void mergePropertiesFrom(Booking updatedBooking) {
        startDate = updatedBooking.getStartDate();
        endDate = updatedBooking.getEndDate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean isDateRangeValid() {
        return DateRangeUtils.isDateRangeValid(startDate, endDate);
    }

    public boolean overlapsWith(Booking newBooking) {
        return endDate.compareTo(newBooking.getStartDate()) >= 0
                && startDate.compareTo(newBooking.getEndDate()) <= 0;
    }
}

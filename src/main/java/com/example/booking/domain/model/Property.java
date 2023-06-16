package com.example.booking.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "properties")
@Entity
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Booking> bookings = new HashSet<>();

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Block> blocks = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return Objects.equals(id, property.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean canAcceptBooking(Booking bookingToBeAccepted) {
        boolean hasOverlappingBookings = bookings.stream()
                .filter(booking -> !booking.equals(bookingToBeAccepted))
                .anyMatch(booking -> booking.overlapsWith(bookingToBeAccepted));

        if (hasOverlappingBookings) {
            return false;
        }

        boolean hasBlocks = blocks.stream().anyMatch(block -> block.overlapsWith(bookingToBeAccepted));
        if (hasBlocks) {
            return false;
        }

        return true;
    }
}

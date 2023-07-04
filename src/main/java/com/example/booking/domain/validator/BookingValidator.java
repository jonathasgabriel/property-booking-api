package com.example.booking.domain.validator;

import com.example.booking.domain.exception.ConstraintViolationException;
import com.example.booking.domain.model.Block;
import com.example.booking.domain.model.Booking;
import com.example.booking.domain.model.Property;
import com.example.booking.domain.repository.BlockRepository;
import com.example.booking.domain.repository.BookingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class BookingValidator {

    private BookingRepository bookingRepository;
    private BlockRepository blockRepository;

    public void validateCreateOrUpdate(Booking booking) {
        boolean isDateRangeInvalid = !booking.isDateRangeValid();
        if (isDateRangeInvalid) {
            throw new ConstraintViolationException("Invalid date range");
        }

        Property property = booking.getProperty();
        Long propertyId = property.getId();
        LocalDate startDate = booking.getStartDate();
        LocalDate endDate = booking.getEndDate();

        List<Block> overlappingBlocks = blockRepository.findAllByPropertyAndByDateRangeOverlap(propertyId,
                startDate, endDate);

        boolean hasOverlappingBlocks = !overlappingBlocks.isEmpty();
        if (hasOverlappingBlocks) {
            throw new ConstraintViolationException("Booking cannot be accepted due to blocks");
        }

        List<Booking> overlappingBookings = bookingRepository
                .findAllByPropertyAndByDateRangeOverlap(propertyId, startDate, endDate)
                .stream().filter(b -> !b.equals(booking)).collect(Collectors.toList());

        boolean hasOverlappingBookings = !overlappingBookings.isEmpty();
        if (hasOverlappingBookings) {
            throw new ConstraintViolationException("Booking cannot be accepted due to overlapping bookings");
        }
    }
}

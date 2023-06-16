package com.example.booking.domain.validator;

import com.example.booking.domain.exception.ConstraintViolationException;
import com.example.booking.domain.model.Booking;
import com.example.booking.domain.model.Property;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BookingValidator {

    public void validateCreateOrUpdate(Booking booking) {
        boolean isDateRangeInvalid = !booking.isDateRangeValid();
        if (isDateRangeInvalid) {
            throw new ConstraintViolationException("Invalid date range");
        }

        Property property = booking.getProperty();

        boolean propertyCannotAcceptBooking = !property.canAcceptBooking(booking);
        if (propertyCannotAcceptBooking) {
            throw new ConstraintViolationException("Booking cannot be accepted due to blocks or overlapping bookings");
        }
    }
}

package com.example.booking.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookingDto {
    private Long id;
    private Long propertyId;
    private LocalDate start;
    private LocalDate end;
}

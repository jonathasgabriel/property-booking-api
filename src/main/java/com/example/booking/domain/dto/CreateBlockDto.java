package com.example.booking.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBlockDto {
    private Long propertyId;
    private LocalDate start;
    private LocalDate end;
}

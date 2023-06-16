package com.example.booking.api.exception;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorResponse {
    String message;
}

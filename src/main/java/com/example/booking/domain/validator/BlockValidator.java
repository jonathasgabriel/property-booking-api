package com.example.booking.domain.validator;

import com.example.booking.domain.exception.ConstraintViolationException;
import com.example.booking.domain.model.Block;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BlockValidator {

    public void validateCreateOrUpdate(Block block) {
        if (!block.isDateRangeValid()) {
            throw new ConstraintViolationException("Invalid date range");
        }
    }
}

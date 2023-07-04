package com.example.booking.domain.validator;

import com.example.booking.domain.exception.ConstraintViolationException;
import com.example.booking.domain.model.Property;
import com.example.booking.domain.repository.PropertyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PropertyValidator {

    private PropertyRepository propertyRepository;

    public void validateCreate(Property property) {
        Property foundProperty = propertyRepository.findPropertyByName(property.getName());

        if (foundProperty == null) {
            return;
        }

        if (foundProperty.equals(property)) {
            return;
        }

        throw new ConstraintViolationException("Property with provided name already exists.");
    }
}

package com.example.booking.domain.repository;

import com.example.booking.domain.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    Property findPropertyByName(String name);
}

package com.example.booking.domain.service;

import com.example.booking.domain.dto.CreatePropertyDto;
import com.example.booking.domain.dto.PropertyDto;
import com.example.booking.domain.model.Property;
import com.example.booking.domain.repository.PropertyRepository;
import com.example.booking.domain.validator.PropertyValidator;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PropertyService {

    private PropertyRepository propertyRepository;
    private PropertyValidator propertyValidator;

    @Transactional
    public PropertyDto create(CreatePropertyDto createPropertyDto) {
        String sanitizedPropertyName = createPropertyDto.getName().trim();
        Property property = Property.builder()
                .name(sanitizedPropertyName)
                .build();

        propertyValidator.validateCreate(property);

        property = propertyRepository.save(property);
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(property, PropertyDto.class);
    }

    public List<PropertyDto> listAll() {
        List<Property> properties = propertyRepository.findAll();
        ModelMapper modelMapper = new ModelMapper();

        List<PropertyDto> propertyDtos = properties.stream()
                .map(property -> modelMapper.map(property, PropertyDto.class))
                .collect(Collectors.toList());

        return propertyDtos;
    }
}

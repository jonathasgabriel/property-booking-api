package com.example.booking.domain.service;

import com.example.booking.domain.dto.BookingDto;
import com.example.booking.domain.dto.CreateBookingDto;
import com.example.booking.domain.dto.RebookBookingDto;
import com.example.booking.domain.exception.ResourceNotFoundException;
import com.example.booking.domain.model.Booking;
import com.example.booking.domain.model.Property;
import com.example.booking.domain.repository.BookingRepository;
import com.example.booking.domain.repository.PropertyRepository;
import com.example.booking.domain.validator.BookingValidator;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookingService {

    private BookingRepository bookingRepository;
    private PropertyRepository propertyRepository;
    private BookingValidator bookingValidator;

    @Transactional
    public BookingDto create(CreateBookingDto createBookingDto) {
        if (!propertyRepository.existsById(createBookingDto.getPropertyId())) {
            throw new ResourceNotFoundException("Property not found");
        }

        Property property = propertyRepository.findById(createBookingDto.getPropertyId()).get();

        Booking booking = Booking.builder()
                .property(property)
                .startDate(createBookingDto.getStart())
                .endDate(createBookingDto.getEnd())
                .build();

        bookingValidator.validateCreateOrUpdate(booking);

        booking = bookingRepository.save(booking);
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(booking, BookingDto.class);
    }

    public BookingDto find(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new ResourceNotFoundException("Booking not found");
        }

        Booking booking = bookingRepository.findById(id).get();
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(booking, BookingDto.class);
    }

    public List<BookingDto> listAll() {
        List<Booking> bookings = bookingRepository.findAll();
        ModelMapper modelMapper = new ModelMapper();
        List<BookingDto> bookingDtos = bookings.stream()
                .map(booking -> modelMapper.map(booking, BookingDto.class))
                .collect(Collectors.toList());

        return bookingDtos;
    }

    @Transactional
    public BookingDto rebook(Long id, RebookBookingDto rebookBookingDto) {
        if (!bookingRepository.existsById(id)) {
            throw new ResourceNotFoundException("Booking not found");
        }

        Booking booking = bookingRepository.findById(id).get();

        Booking updatedBooking = Booking.builder()
                .startDate(rebookBookingDto.getStart())
                .endDate(rebookBookingDto.getEnd())
                .build();

        booking.mergePropertiesFrom(updatedBooking);

        bookingValidator.validateCreateOrUpdate(booking);

        booking = bookingRepository.save(booking);
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(booking, BookingDto.class);
    }

    @Transactional
    public void cancel(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new ResourceNotFoundException("Booking not found");
        }

        bookingRepository.deleteById(id);
    }
}

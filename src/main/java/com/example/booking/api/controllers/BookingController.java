package com.example.booking.api.controllers;

import com.example.booking.domain.dto.*;
import com.example.booking.domain.service.BlockService;
import com.example.booking.domain.service.BookingService;
import com.example.booking.domain.service.PropertyService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BlockService blockService;

    @Autowired
    private PropertyService propertyService;

    @GetMapping("/properties")
    public List<PropertyDto> listProperties() {
        return propertyService.listAll();
    }

    @PostMapping("/properties")
    @ResponseStatus(HttpStatus.CREATED)
    public PropertyDto createProperty(@RequestBody CreatePropertyDto createPropertyDto) {
        return propertyService.create(createPropertyDto);
    }

    @GetMapping("/bookings/{id}")
    public BookingDto findBooking(@PathVariable Long id) {
        return bookingService.find(id);
    }

    @GetMapping("/bookings")
    public List<BookingDto> listBookings() {
        return bookingService.listAll();
    }

    @PostMapping("/bookings")
    @ResponseStatus(HttpStatus.CREATED)
    public BookingDto createBooking(@RequestBody CreateBookingDto createBookingDto) {
        return bookingService.create(createBookingDto);
    }

    @PutMapping("/bookings/{id}")
    public BookingDto rebookBooking(@PathVariable Long id, @RequestBody RebookBookingDto rebookBookingDto) {
        return bookingService.rebook(id, rebookBookingDto);
    }

    @DeleteMapping("/bookings/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelBooking(@PathVariable("id") Long id) {
        bookingService.cancel(id);
    }

    @GetMapping("/blocks/{id}")
    public BlockDto findBlock(@PathVariable Long id) {
        return blockService.find(id);
    }

    @GetMapping("/blocks")
    public List<BlockDto> listBlocks() {
        return blockService.listAll();
    }

    @PostMapping("/blocks")
    @ResponseStatus(HttpStatus.CREATED)
    public BlockDto createBlock(@RequestBody CreateBlockDto createBlockDto) {
        return blockService.create(createBlockDto);
    }

    @PutMapping("/blocks/{id}")
    public BlockDto updateBlock(@PathVariable Long id, @RequestBody UpdateBlockDto updateBlockDto) {
        return blockService.update(id, updateBlockDto);
    }

    @DeleteMapping("/blocks/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBlock(@PathVariable("id") Long id) {
        blockService.delete(id);
    }
}

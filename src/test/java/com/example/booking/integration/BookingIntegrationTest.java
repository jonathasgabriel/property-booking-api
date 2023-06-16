package com.example.booking.integration;

import com.example.booking.domain.dto.CreateBookingDto;
import com.example.booking.domain.model.Booking;
import com.example.booking.domain.model.Property;
import com.example.booking.domain.repository.BookingRepository;
import com.example.booking.domain.repository.PropertyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BookingIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void beforeTest() {
        Property property = Property.builder().name("property").build();
        propertyRepository.save(property);

        Booking firstBooking = Booking.builder()
                .property(property)
                .startDate(LocalDate.of(2023, 5, 5))
                .endDate(LocalDate.of(2023, 5, 6))
                .build();

        Booking secondBooking = Booking.builder()
                .property(property)
                .startDate(LocalDate.of(2023, 5, 2))
                .endDate(LocalDate.of(2023, 5, 3))
                .build();

        Booking thirdBooking = Booking.builder()
                .property(property)
                .startDate(LocalDate.of(2023, 5, 7))
                .endDate(LocalDate.of(2023, 5, 9))
                .build();

        bookingRepository.saveAll(Arrays.asList(firstBooking, secondBooking, thirdBooking));
    }

    @AfterEach
    void afterTest() {
        bookingRepository.deleteAll();
        propertyRepository.deleteAll();
    }

    @Test
    public void itListsAllBookings() throws Exception {
        // given

        // when
        ResultActions response = mockMvc.perform(get("/api/bookings"));

        // then
        Integer expectedNumberOfBookings = Long.valueOf(bookingRepository.count()).intValue();

        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(expectedNumberOfBookings)));
    }

    @Test
    public void itFindsBookingById() throws Exception {
        // given

        // when
        ResultActions response = mockMvc.perform(get("/api/bookings/1"));

        // then
        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.start").exists())
                .andExpect(jsonPath("$.end").exists());
    }

    @Test
    public void itCreatesBooking() throws Exception {
        // given
        Long propertyId = propertyRepository.findAll().get(0).getId();
        LocalDate startDate = LocalDate.of(2023, 5, 11);
        LocalDate endDate = LocalDate.of(2023, 5, 14);

        CreateBookingDto bookingRequest = new CreateBookingDto(propertyId, startDate, endDate);

        Long existingNumberOfBookings = Long.valueOf(bookingRepository.count());

        // when
        ResultActions response = mockMvc.perform(post("/api/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookingRequest)));
        // then
        response
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.start").exists())
                .andExpect(jsonPath("$.end").exists());

        Long expectedNumberOfBookings = existingNumberOfBookings + 1;
        Long actualNumberOfBookings = Long.valueOf(bookingRepository.count());

        Assertions.assertEquals(expectedNumberOfBookings, actualNumberOfBookings);
    }
}

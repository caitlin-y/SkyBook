package com.booking.service;

import com.booking.service.dto.BookingRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@EmbeddedKafka(partitions = 1, topics = {"seat-reservation"})
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createBooking_thenViewStatus_thenCancel() throws Exception {
        BookingRequest request = new BookingRequest();
        request.setPassengerId(1L);
        request.setFlightId(100L);
        request.setSeatId(12L);
        request.setTotalAmount(new BigDecimal("250.00"));

        String response = mockMvc.perform(post("/api/bookings")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andReturn().getResponse().getContentAsString();

        Long bookingId = objectMapper.readTree(response).get("bookingId").asLong();

        mockMvc.perform(get("/api/bookings/" + bookingId + "/status"))
                .andExpect(status().isOk())
                .andExpect(content().string("PENDING"));

        mockMvc.perform(put("/api/bookings/" + bookingId + "/cancel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CANCELLED"));
    }

    @Test
    void getBooking_notFound_returns404() throws Exception {
        mockMvc.perform(get("/api/bookings/9999"))
                .andExpect(status().isNotFound());
    }
}

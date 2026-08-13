package com.booking.service.controller;

import com.booking.service.dto.BookingRequest;
import com.booking.service.dto.BookingResponse;
import com.booking.service.model.Booking;
import com.booking.service.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@Valid @RequestBody BookingRequest request) {
        Booking booking = bookingService.createBooking(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(BookingResponse.from(booking));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> getBooking(@PathVariable Long bookingId) {
        Booking booking = bookingService.viewStatus(bookingId);
        return ResponseEntity.ok(BookingResponse.from(booking));
    }

    @GetMapping("/{bookingId}/status")
    public ResponseEntity<String> getStatus(@PathVariable Long bookingId) {
        Booking booking = bookingService.viewStatus(bookingId);
        return ResponseEntity.ok(booking.getStatus().name());
    }

    @PutMapping("/{bookingId}/cancel")
    public ResponseEntity<BookingResponse> cancelBooking(@PathVariable Long bookingId) {
        Booking booking = bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok(BookingResponse.from(booking));
    }

    @PutMapping("/{bookingId}/confirm")
    public ResponseEntity<BookingResponse> confirmBooking(@PathVariable Long bookingId) {
        Booking booking = bookingService.confirmBooking(bookingId);
        return ResponseEntity.ok(BookingResponse.from(booking));
    }
}

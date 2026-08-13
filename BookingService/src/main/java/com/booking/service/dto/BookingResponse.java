package com.booking.service.dto;

import com.booking.service.model.Booking;
import com.booking.service.model.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BookingResponse {
    private Long bookingId;
    private Long passengerId;
    private Long flightId;
    private Long seatId;
    private LocalDateTime bookingDate;
    private BookingStatus status;
    private BigDecimal totalAmount;

    public static BookingResponse from(Booking booking) {
        BookingResponse r = new BookingResponse();
        r.bookingId = booking.getBookingId();
        r.passengerId = booking.getPassengerId();
        r.flightId = booking.getFlightId();
        r.seatId = booking.getSeatId();
        r.bookingDate = booking.getBookingDate();
        r.status = booking.getStatus();
        r.totalAmount = booking.getTotalAmount();
        return r;
    }

    public Long getBookingId() { return bookingId; }
    public Long getPassengerId() { return passengerId; }
    public Long getFlightId() { return flightId; }
    public Long getSeatId() { return seatId; }
    public LocalDateTime getBookingDate() { return bookingDate; }
    public BookingStatus getStatus() { return status; }
    public BigDecimal getTotalAmount() { return totalAmount; }
}

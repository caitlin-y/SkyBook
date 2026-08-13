package com.booking.service.event;

import java.io.Serializable;

/**
 * Domain event published by the Booking Service when a new booking is
 * created and a seat needs to be reserved by the Flight Service.
 * This decouples Booking from Flight - Booking does not call Flight
 * directly to reserve the seat, it announces intent via Kafka.
 */
public class SeatReservationRequestedEvent implements Serializable {

    private Long bookingId;
    private Long flightId;
    private Long seatId;

    public SeatReservationRequestedEvent() {
    }

    public SeatReservationRequestedEvent(Long bookingId, Long flightId, Long seatId) {
        this.bookingId = bookingId;
        this.flightId = flightId;
        this.seatId = seatId;
    }

    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }

    public Long getFlightId() { return flightId; }
    public void setFlightId(Long flightId) { this.flightId = flightId; }

    public Long getSeatId() { return seatId; }
    public void setSeatId(Long seatId) { this.seatId = seatId; }
}

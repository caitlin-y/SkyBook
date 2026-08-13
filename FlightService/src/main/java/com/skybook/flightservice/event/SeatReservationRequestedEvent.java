package com.skybook.flightservice.event;

public class SeatReservationRequestedEvent {

    // Identifies the booking requesting the seat reservation.
    private Long bookingId;

    // Identifies the flight associated with the booking.
    private Long flightId;

    // Identifies the seat that needs to be reserved.
    private Long seatId;

    // Default constructor required for JSON deserialization.
    public SeatReservationRequestedEvent() {
    }

    // Creates a seat reservation event with the booking, flight and seat details.
    public SeatReservationRequestedEvent(
            Long bookingId,
            Long flightId,
            Long seatId) {
        this.bookingId = bookingId;
        this.flightId = flightId;
        this.seatId = seatId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }
}
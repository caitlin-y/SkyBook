package com.booking.service.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Booking is the aggregate root of the Booking bounded context.
 * It owns its own lifecycle (PENDING -> CONFIRMED -> CANCELLED) and
 * only stores references (IDs) to Passenger and Flight, which are
 * owned by other microservices (bounded contexts).
 */
@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @Column(nullable = false)
    private Long passengerId;

    @Column(nullable = false)
    private Long flightId;

    @Column(nullable = false)
    private Long seatId;

    @Column(nullable = false)
    private LocalDateTime bookingDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    protected Booking() {
        // JPA
    }

    public Booking(Long passengerId, Long flightId, Long seatId, BigDecimal totalAmount) {
        this.passengerId = passengerId;
        this.flightId = flightId;
        this.seatId = seatId;
        this.totalAmount = totalAmount;
        this.bookingDate = LocalDateTime.now();
        this.status = BookingStatus.PENDING;
    }

    // Domain behaviour lives on the entity, not just getters/setters
    public void confirm() {
        if (this.status != BookingStatus.PENDING) {
            throw new IllegalStateException("Only a PENDING booking can be confirmed");
        }
        this.status = BookingStatus.CONFIRMED;
    }

    public void cancel() {
        if (this.status == BookingStatus.CANCELLED) {
            throw new IllegalStateException("Booking is already cancelled");
        }
        this.status = BookingStatus.CANCELLED;
    }

    public Long getBookingId() { return bookingId; }
    public Long getPassengerId() { return passengerId; }
    public Long getFlightId() { return flightId; }
    public Long getSeatId() { return seatId; }
    public LocalDateTime getBookingDate() { return bookingDate; }
    public BookingStatus getStatus() { return status; }
    public BigDecimal getTotalAmount() { return totalAmount; }
}

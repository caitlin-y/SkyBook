package com.skybook.flightservice.entity;

import com.skybook.flightservice.enums.SeatStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Seat {

    // Default constructor required by JPA when creating Seat objects.
    public Seat() {
    }

    // Unique identifier for each seat.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;

    // Seat number shown to the passenger, for example 1A or 1B.
    private String seatNumber;

    // Class of the seat, for example ECONOMY or BUSINESS.
    private String seatClass;

    // Current availability status of the seat.
    // EnumType.STRING stores the status as text in the database.
    @Enumerated(EnumType.STRING)
    private SeatStatus status;

    // Many seats can belong to one flight.
    // JsonIgnore prevents the Flight object from being repeatedly
    // included when returning seat information as JSON.
    @ManyToOne
    @JsonIgnore
    private Flight flight;

    // Returns the flight associated with this seat.
    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    // Returns the unique ID of the seat.
    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    // Returns the seat number.
    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    // Returns the seat class.
    public String getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(String seatClass) {
        this.seatClass = seatClass;
    }

    // Returns the current seat status.
    public SeatStatus getStatus() {
        return status;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }
}
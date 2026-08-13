package com.skybook.flightservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Flight {

    // Default constructor required by JPA when creating Flight objects.
    public Flight() {
    }

    // Unique identifier for each flight.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightId;

    // Airline's flight number, for example SB101.
    private String flightNumber;

    // Airport code where the flight departs from, for example KUL.
    private String origin;

    // Airport code where the flight arrives, for example BKK.
    private String destination;

    // Scheduled departure date and time of the flight.
    private LocalDateTime departureTime;

    // Scheduled arrival date and time of the flight.
    private LocalDateTime arrivalTime;

    // A flight can have multiple seats.
    // The seats are linked to this flight through the "flight" field in Seat.
    @OneToMany(mappedBy = "flight")
    private List<Seat> seats;

    // Returns the unique ID of the flight.
    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    // Returns the flight number.
    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    // Returns the departure airport.
    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    // Returns the destination airport.
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    // Returns the scheduled departure time.
    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    // Returns the scheduled arrival time.
    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    // Returns the list of seats belonging to this flight.
    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
}
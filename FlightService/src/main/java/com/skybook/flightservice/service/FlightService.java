package com.skybook.flightservice.service;

import com.skybook.flightservice.entity.Flight;
import com.skybook.flightservice.entity.Seat;
import com.skybook.flightservice.repository.FlightRepository;
import com.skybook.flightservice.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {

    private final FlightRepository flightRepository;
    private final SeatRepository seatRepository;

    public FlightService(FlightRepository flightRepository,
                         SeatRepository seatRepository) {
        this.flightRepository = flightRepository;
        this.seatRepository = seatRepository;
    }

    // FR-04: Search available flights
    // Finds flights based on the selected origin and destination.
    public List<Flight> searchFlights(String origin, String destination) {
        return flightRepository.findByOriginAndDestination(origin, destination);
    }

    // FR-05: View flight schedule
    // Retrieves the schedule and details of a selected flight.
    public Flight viewSchedule(Long flightId) {
        return flightRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found"));
    }

    // FR-06: Check seat availability
    // Retrieves all seats belonging to the selected flight.
    public List<Seat> checkSeatAvailability(Long flightId) {
        return seatRepository.findByFlightFlightId(flightId);
    }
}
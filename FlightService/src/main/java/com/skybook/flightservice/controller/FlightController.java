package com.skybook.flightservice.controller;

import com.skybook.flightservice.entity.Flight;
import com.skybook.flightservice.entity.Seat;
import com.skybook.flightservice.service.FlightService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    // FR-04: Search available flights
    @GetMapping("/search")
    public List<Flight> searchFlights(
            @RequestParam String origin,
            @RequestParam String destination) {

        return flightService.searchFlights(origin, destination);
    }

    // FR-05: View flight schedule
    @GetMapping("/{flightId}/schedule")
    public Flight viewSchedule(@PathVariable Long flightId) {
        return flightService.viewSchedule(flightId);
    }

    // FR-06: Check seat availability
    @GetMapping("/{flightId}/seats")
    public List<Seat> checkSeatAvailability(@PathVariable Long flightId) {
        return flightService.checkSeatAvailability(flightId);
    }
}
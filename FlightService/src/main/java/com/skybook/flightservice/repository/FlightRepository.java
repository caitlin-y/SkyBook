package com.skybook.flightservice.repository;

import com.skybook.flightservice.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    // Finds flights that match the selected origin and destination.
    List<Flight> findByOriginAndDestination(String origin, String destination);
}
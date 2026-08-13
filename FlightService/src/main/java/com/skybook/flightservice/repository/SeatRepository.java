package com.skybook.flightservice.repository;

import com.skybook.flightservice.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    // Finds all seats belonging to the selected flight.
    List<Seat> findByFlightFlightId(Long flightId);
}
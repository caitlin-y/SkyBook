package com.skybook.flightservice.config;

import com.skybook.flightservice.entity.Flight;
import com.skybook.flightservice.entity.Seat;
import com.skybook.flightservice.enums.SeatStatus;
import com.skybook.flightservice.repository.FlightRepository;
import com.skybook.flightservice.repository.SeatRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    // Loads sample flight and seat data when the application starts.
    @Bean
    CommandLineRunner loadData(
            FlightRepository flightRepository,
            SeatRepository seatRepository) {

        return args -> {

            // Create sample Flight 1 for testing flight search and schedule functions.
            Flight flight1 = new Flight();
            flight1.setFlightNumber("SB101");
            flight1.setOrigin("KUL");
            flight1.setDestination("BKK");
            flight1.setDepartureTime(
                    LocalDateTime.of(2026, 8, 20, 10, 0));
            flight1.setArrivalTime(
                    LocalDateTime.of(2026, 8, 20, 12, 15));

            // Save the flight before creating its seats.
            flightRepository.save(flight1);

            // Create seats for Flight 1 with different availability statuses.
            createSeat("1A", "ECONOMY", SeatStatus.AVAILABLE,
                    flight1, seatRepository);

            createSeat("1B", "ECONOMY", SeatStatus.RESERVED,
                    flight1, seatRepository);

            createSeat("1C", "ECONOMY", SeatStatus.AVAILABLE,
                    flight1, seatRepository);

            createSeat("1D", "BUSINESS", SeatStatus.AVAILABLE,
                    flight1, seatRepository);


            // Create sample Flight 2 for testing multiple flight searches.
            Flight flight2 = new Flight();
            flight2.setFlightNumber("SB202");
            flight2.setOrigin("KUL");
            flight2.setDestination("SIN");
            flight2.setDepartureTime(
                    LocalDateTime.of(2026, 8, 21, 14, 0));
            flight2.setArrivalTime(
                    LocalDateTime.of(2026, 8, 21, 15, 10));

            // Save the second flight before creating its seats.
            flightRepository.save(flight2);

            // Create seats for Flight 2.
            createSeat("2A", "ECONOMY", SeatStatus.AVAILABLE,
                    flight2, seatRepository);

            createSeat("2B", "ECONOMY", SeatStatus.AVAILABLE,
                    flight2, seatRepository);

            createSeat("2C", "ECONOMY", SeatStatus.RESERVED,
                    flight2, seatRepository);

            createSeat("2D", "BUSINESS", SeatStatus.AVAILABLE,
                    flight2, seatRepository);
        };
    }

    // Creates a seat and associates it with the selected flight.
    private void createSeat(
            String seatNumber,
            String seatClass,
            SeatStatus status,
            Flight flight,
            SeatRepository seatRepository) {

        Seat seat = new Seat();

        seat.setSeatNumber(seatNumber);
        seat.setSeatClass(seatClass);
        seat.setStatus(status);
        seat.setFlight(flight);

        // Saves the seat to the Flight Service database.
        seatRepository.save(seat);
    }
}
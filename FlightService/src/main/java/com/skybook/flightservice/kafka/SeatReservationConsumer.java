package com.skybook.flightservice.kafka;

import com.skybook.flightservice.entity.Seat;
import com.skybook.flightservice.enums.SeatStatus;
import com.skybook.flightservice.event.SeatReservationRequestedEvent;
import com.skybook.flightservice.repository.SeatRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class SeatReservationConsumer {

    private final SeatRepository seatRepository;

    public SeatReservationConsumer(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    // Listens for seat reservation requests from the Booking Service.
    @KafkaListener(
            topics = "seat-reservation",
            groupId = "flight-service"
    )
    public void handleSeatReservation(
            SeatReservationRequestedEvent event) {

        // Finds the requested seat using the seat ID from the Kafka event.
        Seat seat = seatRepository.findById(event.getSeatId())
                .orElseThrow(() ->
                        new RuntimeException("Seat not found"));

        // Only available seats can be reserved.
        if (seat.getStatus() == SeatStatus.AVAILABLE) {

            // Updates the seat status and saves the change to the database.
            seat.setStatus(SeatStatus.RESERVED);
            seatRepository.save(seat);

            System.out.println(
                    "Seat " + seat.getSeatId()
                            + " reserved for booking "
                            + event.getBookingId()
            );

        } else {

            // Prevents an already reserved seat from being reserved again.
            System.out.println(
                    "Seat " + seat.getSeatId()
                            + " is already reserved."
            );
        }
    }
}
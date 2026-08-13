package com.booking.service.service;

import com.booking.service.dto.BookingRequest;
import com.booking.service.event.SeatReservationRequestedEvent;
import com.booking.service.model.Booking;
import com.booking.service.repository.BookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class BookingService {

    private static final Logger log = LoggerFactory.getLogger(BookingService.class);

    private final BookingRepository bookingRepository;
    private final KafkaTemplate<String, SeatReservationRequestedEvent> kafkaTemplate;

    @Value("${app.kafka.topic.seat-reservation}")
    private String seatReservationTopic;

    public BookingService(BookingRepository bookingRepository,
                           KafkaTemplate<String, SeatReservationRequestedEvent> kafkaTemplate) {
        this.bookingRepository = bookingRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public Booking createBooking(BookingRequest request) {
        Booking booking = new Booking(
                request.getPassengerId(),
                request.getFlightId(),
                request.getSeatId(),
                request.getTotalAmount()
        );
        Booking saved = bookingRepository.save(booking);

        SeatReservationRequestedEvent event = new SeatReservationRequestedEvent(
                saved.getBookingId(), saved.getFlightId(), saved.getSeatId());
        kafkaTemplate.send(seatReservationTopic, saved.getBookingId().toString(), event);
        log.info("Published SeatReservationRequestedEvent for bookingId={}", saved.getBookingId());

        return saved;
    }

    public Booking viewStatus(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NoSuchElementException("Booking not found: " + bookingId));
    }

    @Transactional
    public Booking cancelBooking(Long bookingId) {
        Booking booking = viewStatus(bookingId);
        booking.cancel();
        return bookingRepository.save(booking);
    }

    @Transactional
    public Booking confirmBooking(Long bookingId) {
        Booking booking = viewStatus(bookingId);
        booking.confirm();
        return bookingRepository.save(booking);
    }
}

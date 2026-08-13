package com.airline.passengerservice.service;

import com.airline.passengerservice.entity.Passenger;
import com.airline.passengerservice.repository.PassengerRepository;
import org.springframework.stereotype.Service;
import com.airline.passengerservice.kafka.PassengerKafkaProducer;
import com.airline.passengerservice.kafka.PassengerRegisteredEvent;

import java.util.List;
import java.util.Optional;

@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;
    private final PassengerKafkaProducer passengerKafkaProducer;

    public PassengerService(
            PassengerRepository passengerRepository,
            PassengerKafkaProducer passengerKafkaProducer) {

        this.passengerRepository = passengerRepository;
        this.passengerKafkaProducer = passengerKafkaProducer;
    }

    // CREATE
    public Passenger savePassenger(Passenger passenger) {

        Passenger savedPassenger = passengerRepository.save(passenger);

        PassengerRegisteredEvent event = new PassengerRegisteredEvent(
                savedPassenger.getId(),
                savedPassenger.getFullName(),
                savedPassenger.getEmail()
        );

        passengerKafkaProducer.sendPassengerRegisteredEvent(event);

        return savedPassenger;
    }

    // READ ALL
    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    // READ BY ID
    public Optional<Passenger> getPassengerById(Long id) {
        return passengerRepository.findById(id);
    }

    // SEARCH BY EMAIL
    public Optional<Passenger> searchPassengerByEmail(String email) {
        return passengerRepository.findByEmail(email);
    }

    // LOGIN
    public Optional<Passenger> login(String email, String password) {
        return passengerRepository.findByEmail(email)
                .filter(passenger -> passenger.getPassword().equals(password));
    }

    // UPDATE
    public Passenger updatePassenger(Long id, Passenger updatedPassenger) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Passenger not found"));

        passenger.setFullName(updatedPassenger.getFullName());
        passenger.setEmail(updatedPassenger.getEmail());
        passenger.setPassword(updatedPassenger.getPassword());
        passenger.setPhoneNumber(updatedPassenger.getPhoneNumber());
        passenger.setPassportNumber(updatedPassenger.getPassportNumber());
        passenger.setNationality(updatedPassenger.getNationality());

        return passengerRepository.save(passenger);
    }

    // DELETE
    public void deletePassenger(Long id) {
        passengerRepository.deleteById(id);
    }
}
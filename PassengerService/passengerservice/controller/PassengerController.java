package com.airline.passengerservice.controller;

import com.airline.passengerservice.entity.Passenger;
import com.airline.passengerservice.service.PassengerService;
import org.springframework.web.bind.annotation.*;
import com.airline.passengerservice.dto.LoginRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/passengers")
public class PassengerController {

    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    // CREATE
    @PostMapping
    public Passenger createPassenger(@RequestBody Passenger passenger) {
        return passengerService.savePassenger(passenger);
    }

    // READ ALL
    @GetMapping
    public List<Passenger> getAllPassengers() {
        return passengerService.getAllPassengers();
    }

    // SEARCH BY EMAIL
    @GetMapping("/search")
    public Optional<Passenger> searchPassengerByEmail(
            @RequestParam String email) {
        return passengerService.searchPassengerByEmail(email);
    }

    // READ BY ID
    @GetMapping("/{id}")
    public Optional<Passenger> getPassengerById(@PathVariable Long id) {
        return passengerService.getPassengerById(id);
    }

    // LOGIN
    @PostMapping("/login")
    public Object login(@RequestBody LoginRequest loginRequest) {

        return passengerService.login(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        ).orElse(null);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Passenger updatePassenger(@PathVariable Long id,
                                     @RequestBody Passenger passenger) {
        return passengerService.updatePassenger(id, passenger);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deletePassenger(@PathVariable Long id) {
        passengerService.deletePassenger(id);
        return "Passenger deleted successfully!";
    }
}
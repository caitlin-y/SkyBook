package com.airline.passengerservice.kafka;

public class PassengerRegisteredEvent {

    private Long passengerId;
    private String fullName;
    private String email;

    public PassengerRegisteredEvent() {
    }

    public PassengerRegisteredEvent(Long passengerId, String fullName, String email) {
        this.passengerId = passengerId;
        this.fullName = fullName;
        this.email = email;
    }

    public Long getPassengerId() {
        return passengerId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }
}
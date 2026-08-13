package com.airline.passengerservice.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PassengerKafkaProducer {

    private final KafkaTemplate<String, PassengerRegisteredEvent> kafkaTemplate;

    public PassengerKafkaProducer(
            KafkaTemplate<String, PassengerRegisteredEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPassengerRegisteredEvent(PassengerRegisteredEvent event) {
        kafkaTemplate.send("passenger-registered", event);
    }
}
# Booking Service

Manages airline bookings: create, view status, cancel. Part of a microservice
system (Passenger, Flight, Booking, Payment) using REST for sync calls and
Kafka for async events (XBAU3134N group project).

## Tech stack
Java 21, Spring Boot 3.3, Spring Web, Spring Data JPA, H2 (dev), Apache Kafka, Maven, Docker.

## Run locally (no Docker)
```bash
mvn clean install
mvn spring-boot:run
```
Runs on `http://localhost:8083`. Needs a Kafka broker reachable at `localhost:9092`
(set `KAFKA_BOOTSTRAP_SERVERS` env var to override).

## Run with Docker (includes Kafka + Zookeeper)
```bash
docker compose up --build
```
This starts Zookeeper, Kafka, and the Booking Service container together.

## Run tests
```bash
mvn test
```
Includes an embedded-Kafka integration test hitting the REST endpoints
(create → status → cancel, and a 404 case).

## Endpoints
| Method | Path | Description |
|---|---|---|
| POST | /api/bookings | Create a booking |
| GET | /api/bookings/{id} | Get full booking |
| GET | /api/bookings/{id}/status | Get status only |
| PUT | /api/bookings/{id}/confirm | Confirm a PENDING booking |
| PUT | /api/bookings/{id}/cancel | Cancel a booking |

## Sample input — Create Booking
`POST /api/bookings`
```json
{
  "passengerId": 1,
  "flightId": 100,
  "seatId": 12,
  "totalAmount": 250.00
}
```

A Postman collection (`postman_collection.json`) is included — import it to test all endpoints.

## Kafka event
On successful creation, publishes `SeatReservationRequestedEvent` to topic
`seat-reservation` with `bookingId`, `flightId`, `seatId`. Flight Service consumes
this to reserve the seat.

## Data ownership
Booking Service owns only the `bookings` table (H2, in-memory). `passengerId`
and `flightId` are references only — no shared database with other services.

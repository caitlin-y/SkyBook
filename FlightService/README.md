# SkyBook - Flight Service

The Flight Service is one of the four microservices in the SkyBook Airline Ticket Booking System. It is responsible for flight information, flight schedules, seat availability, and processing seat reservation events received through Apache Kafka.

## 1. Service Responsibility

The Flight Service  main responsibilities are:

- Managing flight information and schedules
- Searching for available flights
- Providing flight schedule information
- Checking seat availability
- Maintaining seat status within its own database
- Consuming seat reservation events from the Booking Service through Apache Kafka

The Flight Service owns its flight and seat data and does not directly access another microservice's database.

## 2. Technologies Used

| Technology | Purpose |
|---|---|
| Java 21 | Application development |
| Spring Boot | Microservice framework |
| Spring Web MVC | REST API implementation |
| Spring Data JPA | Database access and persistence |
| H2 Database | In-memory database for development and demonstration |
| Apache Kafka | Asynchronous event-driven communication |
| Maven | Build and dependency management |
| Postman | REST API testing |
| IntelliJ IDEA | Development and execution |

## 3. Functional Requirements

The Flight Service implements the following functional requirements:

| Requirement | Function | Description |
|---|---|---|
| FR-04 | Flight Search | Allows users to search for available flights using an origin and destination. |
| FR-05 | Flight Schedule Viewing | Allows users to view the schedule and information of a selected flight. |
| FR-06 | Seat Availability | Allows users to check the seats and their current availability status for a selected flight. |

## 4. REST API Endpoints

The base URL for the Flight Service is:

```text
http://localhost:8082/api/flights
```

### FR-04 - Search Available Flights

**Method:** GET

**Endpoint:**

```text
GET /api/flights/search?origin={origin}&destination={destination}
```

**Example:**

```text
GET http://localhost:8082/api/flights/search?origin=KUL&destination=SIN
```

The endpoint uses origin and destination as request parameters and returns matching flight information.

### FR-05 - View Flight Schedule

**Method:** GET

**Endpoint:**

```text
GET /api/flights/{flightId}/schedule
```

**Example:**

```text
GET http://localhost:8082/api/flights/1/schedule
```

The endpoint uses the flight ID as a path variable and returns the corresponding flight information, including its scheduled departure and arrival times.

### FR-06 - Check Seat Availability

**Method:** GET

**Endpoint:**

```text
GET /api/flights/{flightId}/seats
```

**Example:**

```text
GET http://localhost:8082/api/flights/1/seats
```

The endpoint retrieves the seats associated with the selected flight and returns their seat number, class, and current status.

## 5. Application Structure

The Flight Service follows a layered structure:

```text
Client / Postman
       |
       v
FlightController
       |
       v
FlightService
       |
       v
FlightRepository / SeatRepository
       |
       v
H2 Database
```

### Controller

FlightController exposes the REST API endpoints for FR-04, FR-05 and FR-06.

### Service

FlightService contains the application logic for searching flights, retrieving a flight schedule, and retrieving seats for a selected flight.

### Repository

The repositories provide database access for Flight and Seat entities through Spring Data JPA.

### Entities

The main domain entities are:

- `Flight` - stores flight number, origin, destination, departure time and arrival time.
- `Seat` - stores seat number, seat class, seat status and its associated flight.
- `SeatStatus` - represents the current status of a seat, such as AVAILABLE or RESERVED.

A `Flight` can have multiple `Seat` records, while each `Seat` is associated with one flight.

## 6. Data Persistence

The Flight Service uses an H2 in-memory database. The database is configured as:

```properties
spring.datasource.url=jdbc:h2:mem:flightdb
spring.datasource.username=sa
spring.datasource.password=
```

JPA is used to map the `Flight` and `Seat` entities to database tables. The service owns its own flight and seat data as required by the database-per-microservice approach.

The H2 console is enabled for development and demonstration purposes.

## 7. Kafka Event-Driven Communication

The Flight Service uses Apache Kafka for asynchronous communication with the Booking Service.

### Kafka Configuration

```text
Bootstrap server: localhost:9092
Consumer group: flight-service
Topic: seat-reservation
```

### Seat Reservation Event

The Flight Service consumes `SeatReservationRequestedEvent` messages containing:

- `bookingId`
- `flightId`
- `seatId`

The event is consumed by `SeatReservationConsumer`. When the requested seat is found and its status is `AVAILABLE`, the Flight Service changes the status to `RESERVED` and saves the update to its H2 database.

The communication flow is:

```text
Booking Service
      |
      | SeatReservationRequestedEvent
      v
Apache Kafka
      |
      | seat-reservation topic
      v
Flight Service
      |
      v
Find Seat by seatId
      |
      v
AVAILABLE -> RESERVED
      |
      v
Flight Service H2 Database
```

This allows the Booking Service to request a seat reservation without directly modifying the Flight Service database.

## 8. Running the Flight Service

### Prerequisites

Make sure the following are installed and configured:

- JDK 21
- Apache Maven
- Apache Kafka available at `localhost:9092` when testing the Kafka functionality
- IntelliJ IDEA or another Java IDE
- Postman for API testing

H2 is included as a project dependency and does not require a separate database server.

### Run Using IntelliJ IDEA

1. Open the `FlightService` project in IntelliJ IDEA.
2. Ensure Java 21 is selected for the project.
3. Locate `FlightServiceApplication`.
4. Run the Spring Boot application.
5. The service will start on port `8082`.

### Run Using Maven

From the `FlightService` directory, run:

```bash
mvn spring-boot:run
```

The service should then be available at:

```text
http://localhost:8082
```

## 9. Testing

The Flight Service REST APIs can be tested using Postman.

Recommended tests:

1. **FR-04:** Search for a flight using an origin and destination and verify that matching flight information is returned.
2. **FR-05:** Provide a valid flight ID and verify that the selected flight schedule is returned.
3. **FR-06:** Provide a valid flight ID and verify that the associated seats and their statuses are returned.
4. **Kafka Seat Reservation:** Check a seat's status, create a booking that publishes a `SeatReservationRequestedEvent`, and verify that the Flight Service processes the event and changes the seat status from AVAILABLE to RESERVED.



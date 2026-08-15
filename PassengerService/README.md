Passenger Service 

1. Overview

The Passenger Service is a Spring Boot microservice developed as part of the SkyBook Airline Ticket Booking System.

It is responsible for managing passenger-related operations, including passenger registration, login, profile information, and passenger details.

The service exposes REST APIs that allow other parts of the airline booking system to interact with passenger information.


2. Main Responsibilities:
   
The Passenger Service provides the following functionality:

Passenger registration
Passenger login
Retrieve passenger details
Update passenger information
Manage passenger profiles

3. Passenger Service REST API Endpoints

| Method   | Endpoint               | Description                      |
| -------- | ---------------------- | -------------------------------- |
| `POST`   | `/passengers/register` | Register a new passenger         |
| `POST`   | `/passengers/login`    | Authenticate a passenger         |
| `GET`    | `/passengers/{id}`     | Retrieve passenger details by ID |
| `PUT`    | `/passengers/{id}`     | Update passenger information     |
| `DELETE` | `/passengers/{id}`     | Delete a passenger               |

Publish passenger-related events through Apache Kafka

4. Running the Passenger Service

Using IntelliJ IDEA

1. Open the Passenger Service project in IntelliJ IDEA.

2. Locate the Spring Boot main class:

3. PassengerServiceApplication

4. Right-click the class and select:

Run 'PassengerServiceApplication'

5. Running with Docker

Run:

docker compose up --build

Docker Compose will build and start the Passenger Service together with the other required services and infrastructure.

To check the running containers:

docker compose ps

6. Testing with Postman

Postman can be used to test the Passenger Service REST APIs.

A basic test sequence is:

Register Passenger

       ↓
Login Passenger

       ↓
Retrieve Passenger Details

       ↓
Update Passenger Details

       ↓
Verify Updated Information

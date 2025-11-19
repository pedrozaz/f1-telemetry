# F1 Telemetry Analysis System
A mock backend infrastructure capable of simulating, ingesting, processing, and storing high-frequency Formula 1 telemetry data using an event-driven microservices architecture.

## Tech Stack

- Languages: Java 25 (Spring Boot 3), Python 3.

- Infrastructure: Docker, Docker Compose.

- Messaging: Apache Kafka (Message Broker).

- Database: TimescaleDB (PostgreSQL optimized for time-series data).

## Architecture Overview

### Infrastructure Layer:

- Kafka & Zookeeper: Handles high-throughput data streaming and decoupling of services.

- TimescaleDB: Stores time-stamped telemetry metrics (Speed, RPM, Throttle, Brake) via Hypertable for optimized query performance.

### Microservice 1: Telemetry Producer (Python)

- Reads historical F1 data (CSV).

- Simulates real-time data injection by streaming records to the telemetry.raw Kafka topic.

### Microservice 2: Telemetry Processor (Java)

- Kafka Consumer: Listens to the telemetry.raw topic.

- Logic: Deserializes JSON messages into Java Objects (DTOs).

- Persistence: Maps objects to Entities and saves them to TimescaleDB using Spring Data JPA.

### Microservice 3: Query API (Java)

- REST Controller: Exposes endpoints (e.g., /api/telemetry/{driver}) for external dashboards.

- Data Retrieval: Queries the database to return sorted telemetry history by driver.

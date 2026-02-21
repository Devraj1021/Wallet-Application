# Wallet Service

A high-traffic wallet service built using Spring Boot and PostgreSQL.
This is a closed-loop virtual credit system (e.g., reward points / gaming credits).


## How to Run the Application
1) Database Setup (PostgreSQL - Aiven)

This project uses PostgreSQL (Aiven Cloud instance) as the database.

Steps:
Create a PostgreSQL database in Aiven.
Copy the database credentials (Host, Port, DB name, Username, Password).
Update your application.yml or application.properties:
  spring.datasource.url=jdbc:postgresql://<host>:<port>/<database>
  spring.datasource.username=<username>
  spring.datasource.password=<password>
  spring.jpa.hibernate.ddl-auto=update
  spring.jpa.show-sql=true

2) Seed Data Setup

The project uses a data.sql file located in:
  src/main/resources/data.sql

Spring Boot automatically:
  Creates tables (based on JPA entities)
  Executes data.sql on application startup
  Inserts pre-seeded wallet records
  No manual execution is required.

3) Run the Application

If using Maven:
  mvn clean install
  mvn spring-boot:run
Or run directly from your IDE.

Application will start at:
  http://localhost:8080
  

## Technology Choices & Why

### Spring Boot

I chose Spring Boot because:
  Auto-configuration (JDBC, JPA, Transaction management)
  Embedded server (Tomcat)
  Easy integration with PostgreSQL
  Production-ready features (Actuator, metrics, logging)
  Highly scalable and widely used in enterprise systems
  Spring Boot simplifies configuration and allows faster development while maintaining production-grade standards.

### PostgreSQL (Aiven)

  Reliable and production-grade relational database
  Strong ACID compliance
  Cloud-hosted and managed via Aiven
  Supports high concurrency workloads
  PostgreSQL ensures strong consistency which is critical for wallet systems.


## Concurrency Handling Strategy

Wallet systems require strong consistency to prevent:
  Double spending
  Lost updates
  Race conditions

To handle concurrent updates, I used:
  @Lock(LockModeType.PESSIMISTIC_WRITE)

Why PESSIMISTIC_WRITE?
  Locks the selected row at the database level
  Prevents other transactions from modifying the same wallet record
  Ensures atomic balance updates
  Guarantees no double deduction or credit errors

How it Works:
  When a wallet balance is being updated, 
  The row is locked using PESSIMISTIC_WRITE,
  Other concurrent requests must wait until the transaction completes,
  This ensures serialized updates on the same wallet.

This approach is ideal for financial-like systems where data correctness is more important than raw throughput.

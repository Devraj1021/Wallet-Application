# Wallet Service

A high-traffic wallet service built using **Spring Boot** and **PostgreSQL**.  
This is a closed-loop virtual credit system (e.g., reward points / gaming credits).

---

## 🚀 How to Run the Application

### 1️) Database Setup (PostgreSQL - Aiven)

This project uses **PostgreSQL (Aiven Cloud instance)** as the database.

#### Steps:

1. Create a PostgreSQL database in Aiven.
2. Copy the database credentials:
   - Host
   - Port
   - Database name
   - Username
   - Password
3. Update your `application.yml` or `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://<host>:<port>/<database>
spring.datasource.username=<username>
spring.datasource.password=<password>
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

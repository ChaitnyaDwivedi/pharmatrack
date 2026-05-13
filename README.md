# PharmaTrack-Pharmacy Inventory Management System

##  Project Overview

PharmaTrack is a SpringBoot based Pharmacy Inventory Management System developed to manage medicines, suppliers, stock transactions, and dashboard analytics efficiently.

The system provides REST APIs for:

* Medicine management
* Supplier management
* Stock transaction tracking
* Dashboard analytics
* Inventory monitoring

The project follows layered architecture using:

* Controller Layer
* Service Layer
* Repository Layer

---

#  Features

##  Medicine Management

* Add medicine
* Update medicine
* Delete medicine
* Search medicines
* Filter by category
* Filter by supplier
* Restock medicine
* Dispense medicine
* Low stock alerts
* Expiry alerts
* Prescription required filter
* Pagination & sorting

---

##  Supplier Management

* Add supplier
* Update supplier
* Delete supplier
* Activate/deactivate supplier
* Search supplier
* Fetch supplier medicines

---

## Stock Transaction Management

* View all transactions
* Get transaction by ID
* Filter by medicine
* Filter by transaction type
* Filter by date range

---

## Dashboard Analytics

* Inventory summary
* Stock overview
* Expiry alerts

---

#  Tech Stack

| Technology      | Used |
| --------------- | ---- |
| Java            | 21+  |
| Spring Boot     | 3.x  |
| Spring Data JPA | ✅    |
| Hibernate       | ✅    |
| H2 Database     | ✅    |
| Maven           | ✅    |
| Swagger/OpenAPI | ✅    |
| JUnit 5         | ✅    |
| Mockito         | ✅    |

---

# Project Structure

```text
src/main/java/com/pharmatrack/pharmatrack

├── controller
├── service
├── service/impl
├── repository
├── model
├── dto
├── exception
├── util
```

---

# ▶️ How To Run

##  Clone Repository

```bash
git clone <repository-url>
```

---

## Open Project

Open project in:

* IntelliJ IDEA

---

## Run Application

Run:

```text
PharmatrackApplication.java
```

---

# Swagger API Documentation

```text
http://localhost:8080/swagger-ui/index.html
```

---

#  H2 Database Console

```text
http://localhost:8080/h2-console
```

### Default Configuration

| Property | Value              |
| -------- | ------------------ |
| JDBC URL | jdbc:h2:mem:testdb |
| Username | sa                 |
| Password | (empty)            |

---

# Testing

The project includes:

## Service Layer Testing

Using:

* JUnit 5
* Mockito

## Controller Layer Testing

Using:

* MockMvc
* WebMvcTest

## Repository Layer Testing

Using:

* DataJpaTest

---

# API Modules

| Module           | Status |
| ---------------- | ------ |
| Medicine APIs    | ✅      |
| Supplier APIs    | ✅      |
| Transaction APIs | ✅      |
| Dashboard APIs   | ✅      |

---

# 📈 Key Functionalities

* Inventory management
* Medicine stock tracking
* Supplier management
* Expiry monitoring
* Stock transaction history
* Dashboard analytics
* Exception handling
* Pagination & sorting

---

---

## JaCoCo Code Coverage

This project uses **JaCoCo** for measuring unit and integration test coverage.

### Coverage Achieved

| Metric | Coverage |
|--------|----------|
| Instruction Coverage | 80% |
| Branch Coverage | 77% |

### Tested Components

- Service Layer
- Controller Layer
- Repository Layer
- Global Exception Handler
- Validation & Negative Test Cases

### Tools Used

- JUnit 5
- Mockito
- Spring Boot Test
- MockMvc
- JaCoCo


# 👨‍💻 Developed By

Chaitnya Dwivedi

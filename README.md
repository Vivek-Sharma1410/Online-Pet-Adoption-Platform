# Online Pet Adoption Platform

## Project Overview
The Online Pet Adoption Platform is a Java-based GUI application designed to connect animal shelters with potential adopters. The system provides role-based access for Admin, Shelter, and Adopter users and ensures a safe, consistent adoption process.

---

## Features

### Admin
- Manage users
- Monitor adoption activities
- System overview

### Shelter
- Add and manage pets
- View adoption requests

### Adopter
- Browse available pets
- Apply for adoption
- Prevent duplicate adoptions

---

## Review-2 Enhancements (Key Improvements)

### JDBC Atomic Transactions
- Implemented adoption process using JDBC transactions
- Used:
  - `setAutoCommit(false)`
  - `commit()`
  - `rollback()`
- Ensures all adoption steps succeed or fail together

### Concurrency Handling
- Critical adoption operation protected using `synchronized` method
- Prevents multiple users from adopting the same pet simultaneously

### Controller Layer
- Added controller layer to handle UI-to-DAO interaction
- Improves separation of concerns (MVC-style)

### Validation & Error Handling
- Input validation at UI and controller level
- Graceful failure handling with rollback support

---

## Technologies Used
- Java (Swing)
- JDBC
- SQLite (optional / extensible)
- Git & GitHub

---

## How to Run
1. Open terminal inside `src`
2. Compile:

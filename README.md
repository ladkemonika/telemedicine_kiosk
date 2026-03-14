# Telemedicine Kiosk Application

A full-stack Microservice-ready modular monolith built with Spring Boot and React.js for managing remote doctor-patient consultations.

## Features Included
- **Authentication:** JWT-based stateless authentication with Role-based access control (ADMIN, DOCTOR, PATIENT).
- **Patient Dashboard:** Create profile, view schedules, book appointments, upload medical records, chat with doctors.
- **Doctor Dashboard:** Manage availability, view daily appointments, conduct chats, write and issue prescriptions.
- **Admin Dashboard:** Overview of system users.

## Tech Stack
### Backend
- Java 17, Spring Boot 3.x
- Spring Security (JWT)
- Spring Data JPA & Hibernate
- MySQL Database
- Maven
- Swagger (OpenAPI 3) for API Documentation

### Frontend
- React 18, Vite
- React Router DOM
- Tailwind CSS
- Axios
- Lucide React (Icons)

## Project Structure
```
Telemedicine Kiosk/
├── backend/                  # Spring Boot application
│   ├── src/main/java/com/telemedicine/kiosk/
│   │   ├── config/           # CORS, Security, OpenAPI beans
│   │   ├── controller/       # REST API endpoints
│   │   ├── dto/              # Data Transfer Objects
│   │   ├── entity/           # JPA Entities
│   │   ├── exception/        # Global Exception Handler
│   │   ├── repository/       # Spring Data Interfaces
│   │   ├── security/         # JWT Utilities & Filters
│   │   └── service/          # Business Logic
│   └── pom.xml
│
├── frontend/                 # React application
│   ├── src/
│   │   ├── components/       # Reusable UI (Navbar, Layout, ProtectedRoute)
│   │   ├── context/          # React Context (AuthContext)
│   │   ├── pages/            # View components (Login, Dashboards)
│   │   └── services/         # Axios config setup
│   ├── tailwind.config.js
│   └── package.json
│
├── database_schema.sql       # Initial DB DDL
├── ER_Diagram.md             # Mermaid ER Diagram
├── Telemedicine_Postman_Collection.json
└── README.md
```

## Setup & Run Instructions

### 1. Database Setup
1. Ensure MySQL is running on `localhost:3306`.
2. Create the target database:
   ```sql
   CREATE DATABASE telemedicine_db;
   ```
3. Update `backend/src/main/resources/application.properties` with your MySQL credentials (default configured as `root`/`root`).

### 2. Run Backend
1. Navigate to the `backend` directory:
   ```bash
   cd backend
   ```
2. Run the Spring Boot application using Maven:
   ```bash
   ./mvnw spring-boot:run
   ```
   *(Ensure you have Maven and JDK 17 installed)*
3. Backend will start on `http://localhost:8080`.
4. Swagger API docs automatically available at: `http://localhost:8080/swagger-ui.html`

### 3. Run Frontend
1. Navigate to the `frontend` directory:
   ```bash
   cd frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the Vite development server:
   ```bash
   npm run dev
   ```
4. Application will be available at `http://localhost:5173`.

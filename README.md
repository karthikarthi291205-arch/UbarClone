# Beginner Uber Ride Booking System

A very simple Spring Boot + PostgreSQL project for beginners. It has:
- Passenger CRUD (create, list, find, update, delete)
- Driver CRUD (create, list, find, update, delete)
- Basic ride booking and ride status updates
- REST APIs for Postman and future frontend use

This intentionally does NOT include login, maps, real GPS, payments, DTOs, or advanced matching.

## Requirements
- Java 17+
- Maven
- PostgreSQL
- Postman (optional, for testing APIs)

## 1. Create the database
Open pgAdmin or psql and run:

```sql
CREATE DATABASE uber_db;
```

## 2. Configure PostgreSQL
Open `src/main/resources/application.properties` and update the username/password to match your local PostgreSQL installation.

## 3. Run the application
From this project folder, run:

```bash
mvn spring-boot:run
```

Or open the folder in IntelliJ IDEA / VS Code and run `UberApplication.java`.

The server starts at `http://localhost:8080`.

## 4. Beginner API examples

### Create passenger
POST `http://localhost:8080/api/passengers`
```json
{
  "name": "Asha",
  "phone": "9876543210",
  "email": "asha@example.com"
}
```

### List passengers
GET `http://localhost:8080/api/passengers`

### Create driver
POST `http://localhost:8080/api/drivers`
```json
{
  "name": "Rahul",
  "phone": "9876501234",
  "vehicleNumber": "KA01AB1234"
}
```

### Book a ride
POST `http://localhost:8080/api/rides`
```json
{
  "passengerId": 1,
  "pickup": "College Gate",
  "destination": "City Mall",
  "distanceKm": 5
}
```

The app assigns the first available driver automatically. Add a driver before booking a ride.

### View rides
GET `http://localhost:8080/api/rides`

### Accept / start / complete a ride
PATCH `/api/rides/1/accept`
PATCH `/api/rides/1/start`
PATCH `/api/rides/1/complete`

Use the actual ride ID in the URL.

## Notes
- Fare = base fare ₹30 + ₹12 per kilometre.
- This is a learning project, not a production ride-hailing service.
- The project uses simple entity responses to keep the first version easy to understand.

package com.example.uber;

import java.util.List;
import java.util.Scanner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleMenu implements CommandLineRunner {
    private final Scanner scanner = new Scanner(System.in);
    private final PassengerService passengerService;
    private final DriverService driverService;
    private final RideService rideService;

    public ConsoleMenu(PassengerService passengerService,
                       DriverService driverService,
                       RideService rideService) {
        this.passengerService = passengerService;
        this.driverService = driverService;
        this.rideService = rideService;
    }

    @Override
    public void run(String... args) {
        while (true) {
            System.out.println("\n===== UBER RIDE BOOKING SYSTEM =====");
            System.out.println("1. Register Passenger");
            System.out.println("2. Register Driver");
            System.out.println("3. View Passengers");
            System.out.println("4. View Drivers");
            System.out.println("5. Book Ride");
            System.out.println("6. View Rides");
            System.out.println("7. Accept Ride");
            System.out.println("8. Start Ride");
            System.out.println("9. Complete Ride");
            System.out.println("10. Cancel Ride");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1" -> registerPassenger();
                    case "2" -> registerDriver();
                    case "3" -> passengerService.findAll().forEach(this::showPassenger);
                    case "4" -> driverService.findAll().forEach(this::showDriver);
                    case "5" -> bookRide();
                    case "6" -> rideService.findAll().forEach(this::showRide);
                    case "7" -> updateRideStatus("accept");
                    case "8" -> updateRideStatus("start");
                    case "9" -> updateRideStatus("complete");
                    case "10" -> updateRideStatus("cancel");
                    case "0" -> {
                        System.out.println("Exiting application.");
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void registerPassenger() {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        Passenger passenger = new Passenger(name, phone, email);
        showPassenger(passengerService.save(passenger));
        System.out.println("Passenger registered.");
    }

    private void registerDriver() {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Vehicle number: ");
        String vehicle = scanner.nextLine();

        Driver driver = new Driver(name, phone, vehicle);
        showDriver(driverService.save(driver));
        System.out.println("Driver registered.");
    }

    private void bookRide() {
        System.out.print("Passenger ID: ");
        Long passengerId = Long.parseLong(scanner.nextLine());
        System.out.print("Pickup address: ");
        String pickup = scanner.nextLine();
        System.out.print("Destination: ");
        String destination = scanner.nextLine();
        System.out.print("Distance in kilometres: ");
        double distance = Double.parseDouble(scanner.nextLine());

        RideRequest request = new RideRequest();
        request.setPassengerId(passengerId);
        request.setPickup(pickup);
        request.setDestination(destination);
        request.setDistanceKm(distance);

        showRide(rideService.bookRide(request));
    }

    private void updateRideStatus(String action) {
        System.out.print("Ride ID: ");
        Long id = Long.parseLong(scanner.nextLine());

        Ride ride;
        switch (action) {
            case "accept" -> ride = rideService.accept(id);
            case "start" -> ride = rideService.start(id);
            case "complete" -> ride = rideService.complete(id);
            default -> ride = rideService.cancel(id);
        }
        showRide(ride);
    }

    private void showPassenger(Passenger passenger) {
        System.out.println("ID: " + passenger.getId()
                + " | Name: " + passenger.getName()
                + " | Phone: " + passenger.getPhone()
                + " | Email: " + passenger.getEmail());
    }

    private void showDriver(Driver driver) {
        System.out.println("ID: " + driver.getId()
                + " | Name: " + driver.getName()
                + " | Phone: " + driver.getPhone()
                + " | Vehicle: " + driver.getVehicleNumber()
                + " | Available: " + driver.isAvailable());
    }

    private void showRide(Ride ride) {
        String driverName = ride.getDriver() == null ? "Not assigned" : ride.getDriver().getName();
        System.out.println("Ride ID: " + ride.getId()
                + " | Passenger: " + ride.getPassenger().getName()
                + " | Driver: " + driverName
                + " | From: " + ride.getPickup()
                + " | To: " + ride.getDestination()
                + " | Fare: " + ride.getFare()
                + " | Status: " + ride.getStatus());
    }
}

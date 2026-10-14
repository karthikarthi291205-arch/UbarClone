package com.example.uber;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RideService {
    private static final double BASE_FARE = 30.0;
    private static final double RATE_PER_KM = 12.0;

    private final RideRepository rideRepository;
    private final PassengerRepository passengerRepository;
    private final DriverRepository driverRepository;

    public RideService(RideRepository rideRepository,
                       PassengerRepository passengerRepository,
                       DriverRepository driverRepository) {
        this.rideRepository = rideRepository;
        this.passengerRepository = passengerRepository;
        this.driverRepository = driverRepository;
    }

    @Transactional
    public Ride bookRide(RideRequest request) {
        Passenger passenger = passengerRepository.findById(request.getPassengerId())
                .orElseThrow(() -> new RuntimeException("Passenger not found"));

        if (request.getDistanceKm() <= 0) {
            throw new RuntimeException("Distance must be greater than zero");
        }

        List<Driver> availableDrivers = driverRepository.findByAvailableTrue();
        if (availableDrivers.isEmpty()) {
            throw new RuntimeException("No available drivers");
        }

        Driver driver = availableDrivers.get(0);
        driver.setAvailable(false);
        driverRepository.save(driver);

        Ride ride = new Ride();
        ride.setPassenger(passenger);
        ride.setDriver(driver);
        ride.setPickup(request.getPickup());
        ride.setDestination(request.getDestination());
        ride.setDistanceKm(request.getDistanceKm());
        ride.setFare(BASE_FARE + (RATE_PER_KM * request.getDistanceKm()));
        ride.setStatus(RideStatus.REQUESTED);

        return rideRepository.save(ride);
    }

    public List<Ride> findAll() {
        return rideRepository.findAll();
    }

    public Ride findById(Long id) {
        return rideRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ride not found: " + id));
    }

    public List<Ride> findByPassenger(Long passengerId) {
        return rideRepository.findByPassengerId(passengerId);
    }

    public List<Ride> findByDriver(Long driverId) {
        return rideRepository.findByDriverId(driverId);
    }

    @Transactional
    public Ride accept(Long id) {
        Ride ride = findById(id);
        requireStatus(ride, RideStatus.REQUESTED);
        ride.setStatus(RideStatus.ACCEPTED);
        return rideRepository.save(ride);
    }

    @Transactional
    public Ride start(Long id) {
        Ride ride = findById(id);
        requireStatus(ride, RideStatus.ACCEPTED);
        ride.setStatus(RideStatus.STARTED);
        return rideRepository.save(ride);
    }

    @Transactional
    public Ride complete(Long id) {
        Ride ride = findById(id);
        requireStatus(ride, RideStatus.STARTED);
        ride.setStatus(RideStatus.COMPLETED);
        Driver driver = ride.getDriver();
        driver.setAvailable(true);
        driverRepository.save(driver);
        return rideRepository.save(ride);
    }

    @Transactional
    public Ride cancel(Long id) {
        Ride ride = findById(id);
        if (ride.getStatus() == RideStatus.COMPLETED || ride.getStatus() == RideStatus.CANCELLED) {
            throw new RuntimeException("This ride cannot be cancelled");
        }
        ride.setStatus(RideStatus.CANCELLED);
        Driver driver = ride.getDriver();
        if (driver != null) {
            driver.setAvailable(true);
            driverRepository.save(driver);
        }
        return rideRepository.save(ride);
    }

    private void requireStatus(Ride ride, RideStatus expectedStatus) {
        if (ride.getStatus() != expectedStatus) {
            throw new RuntimeException("Ride must be " + expectedStatus + " before this action");
        }
    }
}

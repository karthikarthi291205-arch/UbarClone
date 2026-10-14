package com.example.uber;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRepository extends JpaRepository<Ride, Long> {
    List<Ride> findByPassengerId(Long passengerId);
    List<Ride> findByDriverId(Long driverId);
}

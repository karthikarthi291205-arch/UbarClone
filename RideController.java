package com.example.uber;

import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rides")
public class RideController {
    private final RideService service;

    public RideController(RideService service) {
        this.service = service;
    }

    @PostMapping
    public Ride book(@RequestBody RideRequest request) {
        return service.bookRide(request);
    }

    @GetMapping
    public List<Ride> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Ride findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/passenger/{passengerId}")
    public List<Ride> findByPassenger(@PathVariable Long passengerId) {
        return service.findByPassenger(passengerId);
    }

    @GetMapping("/driver/{driverId}")
    public List<Ride> findByDriver(@PathVariable Long driverId) {
        return service.findByDriver(driverId);
    }

    @PatchMapping("/{id}/accept")
    public Ride accept(@PathVariable Long id) {
        return service.accept(id);
    }

    @PatchMapping("/{id}/start")
    public Ride start(@PathVariable Long id) {
        return service.start(id);
    }

    @PatchMapping("/{id}/complete")
    public Ride complete(@PathVariable Long id) {
        return service.complete(id);
    }

    @PatchMapping("/{id}/cancel")
    public Ride cancel(@PathVariable Long id) {
        return service.cancel(id);
    }
}

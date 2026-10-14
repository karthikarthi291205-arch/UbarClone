package com.example.uber;

import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/passengers")
public class PassengerController {
    private final PassengerService service;

    public PassengerController(PassengerService service) {
        this.service = service;
    }

    @PostMapping
    public Passenger create(@RequestBody Passenger passenger) {
        return service.save(passenger);
    }

    @GetMapping
    public List<Passenger> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Passenger findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public Passenger update(@PathVariable Long id, @RequestBody Passenger passenger) {
        return service.update(id, passenger);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Passenger deleted";
    }
}

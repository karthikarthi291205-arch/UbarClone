package com.example.uber;

import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {
    private final DriverService service;

    public DriverController(DriverService service) {
        this.service = service;
    }

    @PostMapping
    public Driver create(@RequestBody Driver driver) {
        return service.save(driver);
    }

    @GetMapping
    public List<Driver> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Driver findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public Driver update(@PathVariable Long id, @RequestBody Driver driver) {
        return service.update(id, driver);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Driver deleted";
    }
}

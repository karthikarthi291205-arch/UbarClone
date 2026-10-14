package com.example.uber;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DriverService {
    private final DriverRepository repository;

    public DriverService(DriverRepository repository) {
        this.repository = repository;
    }

    public Driver save(Driver driver) {
        driver.setAvailable(true);
        return repository.save(driver);
    }

    public List<Driver> findAll() {
        return repository.findAll();
    }

    public Driver findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Driver not found: " + id));
    }

    public Driver update(Long id, Driver details) {
        Driver driver = findById(id);
        driver.setName(details.getName());
        driver.setPhone(details.getPhone());
        driver.setVehicleNumber(details.getVehicleNumber());
        return repository.save(driver);
    }

    public void delete(Long id) {
        Driver driver = findById(id);
        repository.delete(driver);
    }
}

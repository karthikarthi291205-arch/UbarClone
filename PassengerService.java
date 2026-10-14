package com.example.uber;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PassengerService {
    private final PassengerRepository repository;

    public PassengerService(PassengerRepository repository) {
        this.repository = repository;
    }

    public Passenger save(Passenger passenger) {
        return repository.save(passenger);
    }

    public List<Passenger> findAll() {
        return repository.findAll();
    }

    public Passenger findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Passenger not found: " + id));
    }

    public Passenger update(Long id, Passenger details) {
        Passenger passenger = findById(id);
        passenger.setName(details.getName());
        passenger.setPhone(details.getPhone());
        passenger.setEmail(details.getEmail());
        return repository.save(passenger);
    }

    public void delete(Long id) {
        Passenger passenger = findById(id);
        repository.delete(passenger);
    }
}

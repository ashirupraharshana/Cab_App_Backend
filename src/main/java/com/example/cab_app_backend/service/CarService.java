package com.example.cab_app_backend.service;

import com.example.cab_app_backend.model.Car;
import com.example.cab_app_backend.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    // Add a new car
    public Car addCar(Car car) {
        return carRepository.save(car);
    }

    // Get all cars
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    // Get car by ID
    public Optional<Car> getCarById(String id) {
        return carRepository.findById(id);
    }

    // Get cars by status
    public List<Car> getCarsByStatus(String status) {
        return carRepository.findByStatus(status);
    }

    // Update car details
    public Car updateCar(String id, Car carDetails) {
        return carRepository.findById(id).map(car -> {
            car.setModel(carDetails.getModel());
            car.setLicensePlate(carDetails.getLicensePlate());
            car.setSeats(carDetails.getSeats());
            car.setCapacity(carDetails.getCapacity());
            car.setStatus(carDetails.getStatus());
            car.setPricePerKm(carDetails.getPricePerKm());
            car.setPhoto(carDetails.getPhoto());
            return carRepository.save(car);
        }).orElse(null);
    }

    // Delete car by ID
    public String deleteCar(String id) {
        if (carRepository.existsById(id)) {
            carRepository.deleteById(id);
            return "Car deleted successfully!";
        }
        return "Car not found!";
    }
    public Car updateCarStatusToInUse(String id) {
        return carRepository.findById(id).map(car -> {
            car.setStatus(1);  // Set status to 1 (Available)
            return carRepository.save(car);
        }).orElse(null);
    }
    public Car updateCarStatusToAvailable(String id) {
        return carRepository.findById(id).map(car -> {
            car.setStatus(0);  // Set status to 0 (Available)
            return carRepository.save(car);
        }).orElse(null);
    }



}

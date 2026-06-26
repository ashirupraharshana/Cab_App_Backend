package com.example.cab_app_backend.repository;

import com.example.cab_app_backend.model.Car;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface CarRepository extends MongoRepository<Car, String> {
    List<Car> findByStatus(String status);
}

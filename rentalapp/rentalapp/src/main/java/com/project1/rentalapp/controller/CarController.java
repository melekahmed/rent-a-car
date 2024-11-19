package com.project1.rentalapp.controller;

import com.project1.rentalapp.model.Car;
import com.project1.rentalapp.repository.CarRepository;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarRepository carRepository;

    // Листване на всички автомобили по локация
    @GetMapping
    public ResponseEntity<List<Car>> getAllCars(@RequestParam(required = false) String location) {
        List<Car> cars;
        if (location != null) {
            cars = carRepository.findByLocation(location);
            System.out.println("Cars found for location " + location + ": " + cars);
        } else {
            cars = carRepository.findAll();
            System.out.println("All cars: " + cars);
        }
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    // Листване на конкретен автомобил по ID
    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        return carRepository.findById(id)
                .map(car -> new ResponseEntity<>(car, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Добавяне на нов автомобил
    @PostMapping
    public ResponseEntity<Car> addCar(@RequestBody Car car) {
        // Проверка за допустима локация
        if (!isValidLocation(car.getLocation())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Car savedCar = carRepository.save(car);
        return new ResponseEntity<>(savedCar, HttpStatus.CREATED);
    }

    // Актуализация на съществуващ автомобил
    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody Car car) {
        return (ResponseEntity<Car>) carRepository.findById(id)
                .map(existingCar -> {
                    if (!isValidLocation(car.getLocation())) {
                        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                    }
                    existingCar.setModel(car.getModel());
                    existingCar.setLocation(car.getLocation());
                    existingCar.setPricePerDay(car.getPricePerDay());
                    Car updatedCar = carRepository.save(existingCar);
                    return new ResponseEntity<>(updatedCar, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Изтриване на автомобил от системата (меко изтриване)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        return carRepository.findById(id)
                .map(car -> {
                    carRepository.delete(car); // Меко изтриване
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Метод за проверка на допустима локация
    private boolean isValidLocation(String location) {
        return location.equals("Пловдив") || location.equals("София") || location.equals("Варна") || location.equals("Бургас");
    }
}
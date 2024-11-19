package com.project1.rentalapp.service;



import com.project1.rentalapp.model.Car;
import com.project1.rentalapp.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    // Метод за предоставяне на налични автомобили по локация
    public List<Car> getAvailableCars(String location) {
        return carRepository.findByLocation(location); // Предполага се, че имате метод за търсене по локация
    }

    // Метод за оценка на цената на наема
    public double calculateRentalPrice(Long carId, int rentalDays) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new IllegalArgumentException("Car not found"));

        double pricePerDay = car.getPricePerDay();
        return pricePerDay * rentalDays; // Обща цена = дневна цена * брой дни
    }
}
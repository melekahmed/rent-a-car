package com.project1.rentalapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String location; // Пловдив, София, Варна, Бургас

    @Column(nullable = false)
    private double pricePerDay;

    // Конструктори
    public Car() {}

    public Car(String model, String location, double price_per_day ) {
        this.model = model;
        this.location = location;
        this.pricePerDay = pricePerDay;
    }

    // Гетъри и сетъри
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay ) {
        this.pricePerDay= pricePerDay;
    }

}
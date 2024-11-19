package com.project1.rentalapp.repository;

import com.project1.rentalapp.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    // Метод за намиране на оферти по ID на автомобила
    List<Offer> findByCarId(Long carId);

    // Метод за намиране на оферти по име на клиента
    List<Offer> findByCustomerName(String customerName);
}

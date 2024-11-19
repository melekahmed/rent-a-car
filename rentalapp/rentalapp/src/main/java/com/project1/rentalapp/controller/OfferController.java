package com.project1.rentalapp.controller;
import com.project1.rentalapp.model.Offer;
import com.project1.rentalapp.repository.OfferRepository;
import com.project1.rentalapp.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/offers")
public class OfferController {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private CarService carService;

    // Създаване на нова оферта
    @PostMapping
    public ResponseEntity<Offer> createOffer(@RequestBody Offer offer) {
        // Изчисляване на общата цена
        double totalPrice = carService.calculateRentalPrice(offer.getCarId(), offer.getRentalDays());
        offer.setTotalPrice(totalPrice);
        Offer savedOffer = offerRepository.save(offer);
        return new ResponseEntity<>(savedOffer, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<Offer>> getOffersByCustomerName(@RequestParam String customerName) {
        // Извикване на метода, който търси оферти по име на клиента
        List<Offer> offers = offerRepository.findByCustomerName(customerName);

        // Проверка дали списъкът с оферти е празен
        if (offers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Връщаме статус 204, ако няма оферти
        }

        // Връщаме офертите с статус 200
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    // Листване на конкретна оферта по ID
    @GetMapping("/{id}")
    public ResponseEntity<Offer> getOfferById(@PathVariable Long id) {
        return offerRepository.findById(id)
                .map(offer -> new ResponseEntity<>(offer, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Актуализация на съществуваща оферта
    @PutMapping("/{id}")
    public ResponseEntity<Offer> updateOffer(@PathVariable Long id, @RequestBody Offer offer) {
        return offerRepository.findById(id)
                .map(existingOffer -> {
                    existingOffer.setCustomerName(offer.getCustomerName());
                    existingOffer.setCarId(offer.getCarId());
                    existingOffer.setRentalDays(offer.getRentalDays());

                    // Пресмятане на новата цена
                    double totalPrice = carService.calculateRentalPrice(offer.getCarId(), offer.getRentalDays());
                    existingOffer.setTotalPrice(totalPrice);

                    existingOffer.setStatus(offer.getStatus());
                    Offer updatedOffer = offerRepository.save(existingOffer);
                    return new ResponseEntity<>(updatedOffer, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Изтриване на оферта
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffer(@PathVariable Long id) {
        return offerRepository.findById(id)
                .map(offer -> {
                    offerRepository.delete(offer);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}